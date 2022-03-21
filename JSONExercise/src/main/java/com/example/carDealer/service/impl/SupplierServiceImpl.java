package com.example.carDealer.service.impl;

import com.example.carDealer.models.dto.SupplierLocalDTO;
import com.example.carDealer.models.dto.seed.SupplierSeedDTO;
import com.example.carDealer.models.entity.Part;
import com.example.carDealer.models.entity.Supplier;
import com.example.carDealer.repository.SupplierRepository;
import com.example.carDealer.service.SupplierService;
import com.example.carDealer.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
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
        Random random = new Random();
        long count = supplierRepository.count();
        long randomId =random
                .nextLong(1,count+1);
        return supplierRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<SupplierLocalDTO> findAllLocalSupplierAndNumberOfParts() {
        ModelMapper mapper = new ModelMapper();

        Converter<Collection<Part>, Integer> collectionToSize = c -> c.getSource().size();

        TypeMap<Supplier, SupplierLocalDTO> propertyMapper = mapper
                .createTypeMap(Supplier.class, SupplierLocalDTO.class);

        propertyMapper.addMappings(
                mapperM -> mapperM.using(collectionToSize)
                        .map(Supplier::getParts, SupplierLocalDTO::setPartsCount)
        );

        return supplierRepository.findAllByImporterIsFalse()
                .stream()
                .map(supplier -> mapper.map(supplier, SupplierLocalDTO.class))
                .collect(Collectors.toList());
    }
}
