package com.example.carDealer.service.impl;

import com.example.carDealer.model.dto.car.CarSeedDTO;
import com.example.carDealer.model.dto.car.CarToyotaDTO;
import com.example.carDealer.model.dto.car.CarViewRootToyotaDTO;
import com.example.carDealer.model.entity.Car;
import com.example.carDealer.reposiitory.CarRepository;
import com.example.carDealer.service.CarService;
import com.example.carDealer.service.PartService;
import com.example.carDealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final PartService partService;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, ValidationUtil validationUtil, PartService partService) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.partService = partService;
    }

    @Override
    public void seedCar(List<CarSeedDTO> cars) {
        cars
                .stream()
                .filter(validationUtil::isValid)
                .map(carSeedDTO -> {
                    Car car = modelMapper.map(carSeedDTO, Car.class);
                    car.setParts(partService.findRandomParts());
                    return car;
                })
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
    public CarViewRootToyotaDTO findAllCarToyotaOrderByModelAndTravelDistanceDesc() {
        CarViewRootToyotaDTO carViewRootToyotaDTO = new CarViewRootToyotaDTO();

        carViewRootToyotaDTO
                .setCars( carRepository
                        .findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota")
                         .stream()
                         .map(car -> modelMapper.map(car, CarToyotaDTO.class))
                         .collect(Collectors.toList()));

      return carViewRootToyotaDTO;
    }

    @Override
    public Long getEntityCount() {
        return this.carRepository.count();
    }
}
