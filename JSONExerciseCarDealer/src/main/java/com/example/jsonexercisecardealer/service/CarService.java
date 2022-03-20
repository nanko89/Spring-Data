package com.example.jsonexercisecardealer.service;

import java.io.IOException;

public interface CarService {
    void seedCarData() throws IOException;

    Long findRandomCarId();
}
