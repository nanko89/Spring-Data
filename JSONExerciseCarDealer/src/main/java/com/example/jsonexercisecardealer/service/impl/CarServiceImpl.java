package com.example.jsonexercisecardealer.service.impl;

import com.example.jsonexercisecardealer.models.dto.seed.CarSeedDTO;
import com.example.jsonexercisecardealer.models.entity.Car;
import com.example.jsonexercisecardealer.repository.CarRepository;
import com.example.jsonexercisecardealer.service.CarService;
import com.example.jsonexercisecardealer.service.PartService;
import com.example.jsonexercisecardealer.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final PartService partService;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, PartService partService) {
        this.carRepository = carRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.partService = partService;
    }

    @Override
    public void seedCarData() throws IOException {
        if (carRepository.count() > 0){
            return;
        }

        String PATH_CAR = "src/main/resources/Files/cars.json";

        String readString = Files.readString(Path.of(PATH_CAR));

        Arrays.stream(gson.fromJson(readString, CarSeedDTO[].class))
                .filter(validationUtil::isValid)
                .map(this::getCarDto)
                .forEach(carRepository::save);
    }

    @Override
    public Long findRandomCarId() {
        return ThreadLocalRandom
                .current()
                .nextLong(0,carRepository.count()+1);
    }

    private Car getCarDto(CarSeedDTO carSeedDTO) {

        Car car = modelMapper.map(carSeedDTO, Car.class);

        car.setParts(partService.findRandomPart());

        return car;

    }
}
