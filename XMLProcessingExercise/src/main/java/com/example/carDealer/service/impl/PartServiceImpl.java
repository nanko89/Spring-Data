package com.example.carDealer.service.impl;

import com.example.carDealer.model.dto.part.PartSeedDTO;
import com.example.carDealer.model.entity.Part;
import com.example.carDealer.reposiitory.PartRepository;
import com.example.carDealer.service.PartService;
import com.example.carDealer.service.SupplierService;
import com.example.carDealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final SupplierService supplierService;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, ValidationUtil validationUtil, SupplierService supplierService) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.supplierService = supplierService;
    }

    @Override
    public long getEntityCount() {
        return 0;
    }

    @Override
    public void seedPart(List<PartSeedDTO> parts) {
        parts
                .stream()
                .filter(validationUtil::isValid)
                .map(partSeedDTO -> {
                    Part part = modelMapper.map(partSeedDTO, Part.class);
                    part.setSupplier(supplierService.findRandomSupplier());
                    return part;
                })
                .forEach(partRepository::save);
    }

    @Override
    public Set<Part> findRandomParts() {

        Set<Part> parts = new HashSet<>();

        Random random = new Random();

        long size = partRepository.count();

        long randomCount = random.nextLong(10,20);

        for (int i = 0; i < randomCount; i++) {

        long randomId = random.nextLong(1, size + 1);

        partRepository.findById(randomId).ifPresent(parts::add);
        }

        return parts;
    }
}
