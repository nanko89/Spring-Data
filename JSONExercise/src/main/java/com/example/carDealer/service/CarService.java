package com.example.carDealer.service;

import java.io.IOException;

public interface CarService {
    void seedCarData() throws IOException;

    Long findRandomCarId();
}
