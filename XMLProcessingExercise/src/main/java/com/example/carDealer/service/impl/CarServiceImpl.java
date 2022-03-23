package com.example.carDealer.service.impl;

import com.example.carDealer.model.dto.car.CarSeedDTO;
import com.example.carDealer.model.entity.Car;
import com.example.carDealer.reposiitory.CarRepository;
import com.example.carDealer.service.CarService;
import com.example.carDealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCar(List<CarSeedDTO> cars) {
        cars
                .stream()
                .filter(validationUtil::isValid)
                .map(carSeedDTO -> modelMapper.map(carSeedDTO, Car.class))
                .forEach(carRepository::save);
    }

    @Override
    public Long getEntityCount() {
        return this.carRepository.count();
    }
}
