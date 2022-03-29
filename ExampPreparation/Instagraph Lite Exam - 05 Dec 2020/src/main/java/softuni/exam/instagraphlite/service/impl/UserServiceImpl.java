package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.json.UserSeedDto;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;

@Service
public class UserServiceImpl implements UserService {

    public static final String USER_FILE_PATH = "src/main/resources/files/users.json";

    private final UserRepository userRepository;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public UserServiceImpl(UserRepository userRepository, PictureService pictureService, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.userRepository = userRepository;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(USER_FILE_PATH));
    }

    @Override
    public String importUsers() throws IOException {

        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson.fromJson(readFromFileContent(), UserSeedDto[].class))
                .filter(userSeedDto -> {

                    boolean isValid = validationUtil.isValid(userSeedDto)
                            && !isExistEntity(userSeedDto.getUsername())
                            && pictureService.isExistEntity(userSeedDto.getProfilePicture());

                    sb.append(isValid
                            ? String.format("Successfully imported User: %s", userSeedDto.getUsername())
                            : "Invalid User");

                    sb.append(System.lineSeparator());

                    return isValid;
                })
                .map(userSeedDto -> {
                    User user = modelMapper.map(userSeedDto, User.class);
                    user.setProfilePicture(pictureService.findByPath(userSeedDto.getProfilePicture()));
                    return user;
                })
                .forEach(userRepository::save);

        return sb.toString();
    }

    @Override
    public boolean isExistEntity(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public String exportUsersWithTheirPosts() {

        StringBuilder sb = new StringBuilder();

        userRepository.findAllWithCountOfPostsDescAndUserId()
                .forEach(user -> {
                    sb.append(String.format("User: %s%n" +
                            "Post count: %d%n", user.getUsername(), user.getPosts().size()));

                    user.getPosts()
                            .stream()
                            .sorted(Comparator.comparingDouble(a -> a.getPicture().getSize()))
                            .forEach(post -> {
                                sb.append(String.format("==Post Details:%n" +
                                                "----Caption: %s%n" +
                                                "----Picture Size: %.2f%n", post.getCaption()
                                        , post.getPicture().getSize()));
                            });
                });

        return sb.toString();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
