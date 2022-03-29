package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.CarSeedDto;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    public static final String CAR_PATH = "src/main/resources/files/json/cars.json";

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return Files
                .readString(Path.of(CAR_PATH));
    }

    @Override
    public String importCars() throws IOException {

        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson
                        .fromJson(readCarsFileContent(), CarSeedDto[].class))
                .filter(carSeedDto -> {
                    boolean isValid = validationUtil.isValid(carSeedDto);
                    sb.append(isValid
                            ? String.format("Successfully imported car - %s - %s",
                            carSeedDto.getMake(), carSeedDto.getModel())
                            : "Invalid car");
                    sb.append(System.lineSeparator());
                    return isValid;
                })
                .map(carSeedDto -> modelMapper.map(carSeedDto, Car.class))
                .forEach(carRepository::save);

        return sb.toString();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        return carRepository.findCarOrderByPictureThenByMake()
                .stream()
                .map(car -> String.format("Car make - %s, model - %s%n" +
                                "\tKilometers - %d%n" +
                                "\tRegistered on - %s%n" +
                                "\tNumber of pictures - %d%n",
                        car.getMake(), car.getModel(), car.getKilometers(),
                        car.getRegisteredOn(), car.getPictures().size()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public Car findById(Long carId) {
        return carRepository
                .findById(carId)
                .orElse(null);
    }
}
