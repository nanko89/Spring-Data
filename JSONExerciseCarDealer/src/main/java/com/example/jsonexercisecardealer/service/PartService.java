package com.example.jsonexercisecardealer.service;

import com.example.jsonexercisecardealer.models.entity.Part;

import java.io.IOException;
import java.util.Set;

public interface PartService {
    void seedPartData() throws IOException;
     Set<Part> findRandomPart();
}
