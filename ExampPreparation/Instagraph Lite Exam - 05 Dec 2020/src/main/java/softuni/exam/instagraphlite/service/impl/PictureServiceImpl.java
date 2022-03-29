package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.json.PictureSeedDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class PictureServiceImpl implements PictureService {

    public static final String PICTURE_FILE_PATH = "src/main/resources/files/pictures.json";

    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files
                .readString(Path.of(PICTURE_FILE_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readFromFileContent(), PictureSeedDto[].class))
                .filter(pictureSeedDto -> {

                    boolean isValid = validationUtil.isValid(pictureSeedDto)
                            && !isExistEntity(pictureSeedDto.getPath());

                    sb.append( isValid
                    ? String.format("Successfully imported Picture, with size %.2f", pictureSeedDto.getSize())
                            : "Invalid Picture");

                    sb.append(System.lineSeparator());

                return isValid;

                })
                .map(pictureSeedDto -> modelMapper.map(pictureSeedDto, Picture.class))
                .forEach(pictureRepository::save);

        return sb.toString();
    }

    @Override
    public boolean isExistEntity(String path) {
        return pictureRepository.existsByPath(path);
    }

    @Override
    public String exportPictures() {

        StringBuilder sb = new StringBuilder();

        pictureRepository.findAllBySizeIsGreaterThanOrderBySize(30000.0)
                .forEach(p -> sb.append(String.format("%.2f – %s%n",p.getSize(), p.getPath())));

        return sb.toString();
    }

    @Override
    public Picture findByPath(String profilePicture) {
        return pictureRepository.findByPath(profilePicture);
    }
}
