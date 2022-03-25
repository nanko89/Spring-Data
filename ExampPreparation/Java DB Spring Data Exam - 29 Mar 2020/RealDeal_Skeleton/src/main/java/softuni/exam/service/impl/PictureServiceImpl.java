package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.PictureSeedDto;
import softuni.exam.models.entity.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class PictureServiceImpl implements PictureService {

    public static final String PICTURE_PATH = "src/main/resources/files/json/pictures.json";

    private final PictureRepository pictureRepository;
    private final CarService carService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;


    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, CarService carService, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.pictureRepository = pictureRepository;
        this.carService = carService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return Files
                .readString(Path.of(PICTURE_PATH));
    }

    @Override
    public String importPictures() throws IOException {

        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson
                        .fromJson(readPicturesFromFile(), PictureSeedDto[].class))
                .filter(pictureSeedDto -> {
                    boolean isValid = validationUtil.isValid(pictureSeedDto);

                    sb.append(isValid
                            ? String.format("Successfully import picture - %s", pictureSeedDto.getName())
                            : "Invalid picture");

                    sb.append(System.lineSeparator());

                    return isValid;
                })
                .map(pictureSeedDto -> {
                    Picture picture = modelMapper
                            .map(pictureSeedDto, Picture.class);
                    picture
                            .setCar(carService.findById(pictureSeedDto.getCar()));

                    return picture;
                })
                .forEach(pictureRepository::save);

        return sb.toString();
    }
}
