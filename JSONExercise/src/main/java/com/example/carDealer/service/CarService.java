package com.example.carDealer.service;

import com.example.carDealer.models.dto.CarInfoDTO;
import com.example.carDealer.models.dto.CarMakeDTO;
import com.example.carDealer.models.entity.Car;

import java.io.IOException;
import java.util.List;

public interface CarService {
    void seedCarData() throws IOException;

    Car findRandomCar();

    List<CarMakeDTO> findCarMakeToyotaOrderByModelAscAndTravelDistance();

    List<CarInfoDTO> findAllCarWithTheirParts();
}
