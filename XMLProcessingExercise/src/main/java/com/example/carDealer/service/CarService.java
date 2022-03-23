package com.example.carDealer.service;

import com.example.carDealer.model.dto.car.CarSeedDTO;

import java.util.List;

public interface CarService {
    void seedCar(List<CarSeedDTO> cars);

    Long getEntityCount();
}
