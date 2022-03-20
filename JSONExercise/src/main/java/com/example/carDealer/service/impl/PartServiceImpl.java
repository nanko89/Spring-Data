package com.example.carDealer.service.impl;

import com.example.carDealer.models.dto.seed.PartSeedDTO;
import com.example.carDealer.models.entity.Part;
import com.example.carDealer.models.entity.Supplier;
import com.example.carDealer.repository.PartRepository;
import com.example.carDealer.service.PartService;
import com.example.carDealer.service.SupplierService;
import com.example.carDealer.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


@Service
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final SupplierService supplierService;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, SupplierService supplierService) {
        this.partRepository = partRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.supplierService = supplierService;
    }


    @Override
    public void seedPartData() throws IOException {
        if (partRepository.count() > 0) {
            return;
        }

        String PATH_PART = "src/main/resources/Files/car.dealer/parts.json";

        String fileContent = Files.readString(Path.of(PATH_PART));

        Arrays.stream(gson.fromJson(fileContent, PartSeedDTO[].class))
                .filter(validationUtil::isValid)
                .map(this::getPartDTO)
                .forEach(partRepository::save);

    }

    private Part getPartDTO(PartSeedDTO partSeedDTO) {
        Part part = modelMapper.map(partSeedDTO, Part.class);
        Supplier supplier = supplierService.findRandomSupplier();
        part.setSupplier(supplier);
        return part;
    }

    @Override
    @Transactional
    public Set<Part> findRandomPart() {
        Set<Part> parts = new HashSet<>();
        Random random = new Random();
        long count = partRepository.count();

        int partCount = random.nextInt(3,6);

        for (int i = 0; i < partCount; i++) {

            long randomId = random.nextLong(1,count+1);

            parts
                    .add(partRepository.findById(randomId)
                            .orElse(null));
        }
        return parts;
    }
}
