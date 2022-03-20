package com.example.carDealer.service.impl;

import com.example.carDealer.models.dto.seed.SupplierSeedDTO;
import com.example.carDealer.models.entity.Supplier;
import com.example.carDealer.repository.SupplierRepository;
import com.example.carDealer.service.SupplierService;
import com.example.carDealer.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public SupplierServiceImpl(SupplierRepository supplierRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.supplierRepository = supplierRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedSupplierData() throws IOException {
        if (supplierRepository.count() > 0){
            return;
        }

        String PATH_SUPPLIER = "src/main/resources/Files/car.dealer/suppliers.json";

        String readString = Files.readString(Path.of(PATH_SUPPLIER));

        Arrays.stream(gson.fromJson(readString, SupplierSeedDTO[].class))
                .filter(validationUtil::isValid)
                .map(supplierSeedDTO -> modelMapper.map(supplierSeedDTO, Supplier.class))
                .forEach(supplierRepository::save);
    }

    @Override
    public Supplier findRandomSupplier() {
         long randomId = ThreadLocalRandom
                .current()
                .nextLong(supplierRepository.count()+1);
        return supplierRepository.findById(randomId).orElse(null);
    }
}
