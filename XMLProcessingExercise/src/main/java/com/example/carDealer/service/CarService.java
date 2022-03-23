package com.example.carDealer.service;

import com.example.carDealer.model.dto.car.CarSeedDTO;
import com.example.carDealer.model.dto.car.CarViewRootToyotaDTO;
import com.example.carDealer.model.dto.car.CarViewRootWithPartsDTO;
import com.example.carDealer.model.entity.Car;

import java.util.List;

public interface CarService {
    void seedCar(List<CarSeedDTO> cars);

    Long getEntityCount();

    Car findRandomCar();

    CarViewRootToyotaDTO findAllCarToyotaOrderByModelAndTravelDistanceDesc();

    CarViewRootWithPartsDTO findAllCarsWithTheirParts();
}
