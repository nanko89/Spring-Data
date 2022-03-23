package com.example.carDealer.service;

import com.example.carDealer.model.dto.part.PartSeedDTO;
import com.example.carDealer.model.entity.Part;

import java.util.List;
import java.util.Set;

public interface PartService {
    long getEntityCount();

    void seedPart(List<PartSeedDTO> parts);

    Set<Part> findRandomParts();
}
