package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.xml.PostSeedRootDto;
import softuni.exam.instagraphlite.models.entity.Post;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtil;
import softuni.exam.instagraphlite.util.XmlParse;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PostServiceImpl implements PostService {

    public static final String POST_FILE_PATH = "src/main/resources/files/posts.xml";

    private final PostRepository postRepository;
    private final UserService userService;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParse xmlParse;

    public PostServiceImpl(PostRepository postRepository, UserService userService, PictureService pictureService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParse xmlParse) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParse = xmlParse;
    }

    @Override
    public boolean areImported() {
        return this.postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(POST_FILE_PATH));
    }

    @Override
    public String importPosts() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();

        PostSeedRootDto postSeedRootDto = xmlParse.fromFile(POST_FILE_PATH, PostSeedRootDto.class);

        postSeedRootDto.getPosts()
                .stream()
                .filter(postSeedDto -> {
                    boolean isValid = validationUtil.isValid(postSeedDto)
                            && pictureService.isExistEntity(postSeedDto.getPicture().getPath())
                            && userService.isExistEntity(postSeedDto.getUser().getUsername());

                    sb.append(isValid
                                    ? String.format("Successfully imported Post, made by %s", postSeedDto.getUser().getUsername())
                                    : "Invalid Post")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(postSeedDto -> {
                    Post post = modelMapper.map(postSeedDto, Post.class);
                    post.setPicture(pictureService.findByPath(postSeedDto.getPicture().getPath()));
                    post.setUser(userService.findByUsername(postSeedDto.getUser().getUsername()));
                return post;
                })
                .forEach(postRepository::save);

        return sb.toString();
    }
}
