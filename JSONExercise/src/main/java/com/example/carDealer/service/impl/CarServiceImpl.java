package com.example.carDealer.service.impl;

import com.example.carDealer.models.dto.CarInfoDTO;
import com.example.carDealer.models.dto.CarMakeDTO;
import com.example.carDealer.models.dto.seed.CarSeedDTO;
import com.example.carDealer.models.entity.Car;
import com.example.carDealer.repository.CarRepository;
import com.example.carDealer.service.CarService;
import com.example.carDealer.service.PartService;
import com.example.carDealer.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

        String PATH_CAR = "src/main/resources/Files/car.dealer/cars.json";

        String readString = Files.readString(Path.of(PATH_CAR));

        Arrays.stream(gson.fromJson(readString, CarSeedDTO[].class))
                .filter(validationUtil::isValid)
                .map(this::getCarDto)
                .forEach(carRepository::save);
    }

    @Override
    public Car findRandomCar() {
        Random random = new Random();
        long count = carRepository.count();
        long randomId = random
                .nextLong(1,  count + 1);
        return carRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<CarMakeDTO> findCarMakeToyotaOrderByModelAscAndTravelDistance() {
        return carRepository.findAllByMakeOrderByModelTravelledDistanceDes("Toyota")
                .stream()
                .map(car -> modelMapper.map(car, CarMakeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<CarInfoDTO> findAllCarWithTheirParts() {
        return carRepository.findAll()
                .stream()
                .map(car -> modelMapper.map(car, CarInfoDTO.class))
                .collect(Collectors.toList());
    }

    private Car getCarDto(CarSeedDTO carSeedDTO) {

        Car car = modelMapper.map(carSeedDTO, Car.class);

        car.setParts(partService.findRandomPart());

        return car;

    }
}
