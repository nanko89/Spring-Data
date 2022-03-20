package com.example.carDealer.service;

import com.example.carDealer.models.entity.Part;

import java.io.IOException;
import java.util.Set;

public interface PartService {
    void seedPartData() throws IOException;
     Set<Part> findRandomPart();
}
