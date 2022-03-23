package com.example.carDealer.service.impl;

import com.example.carDealer.model.dto.supplier.SupplierSeedDTO;
import com.example.carDealer.model.entity.Supplier;
import com.example.carDealer.reposiitory.SupplierRepository;
import com.example.carDealer.service.SupplierService;
import com.example.carDealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public long getEntityCount() {
        return this.supplierRepository.count();
    }

    @Override
    public void seedSupplier(List<SupplierSeedDTO> suppliers) {
        suppliers
                .stream()
                .filter(validationUtil::isValid)
                .map(supplierSeedDTO -> modelMapper.map(supplierSeedDTO, Supplier.class))
                .forEach(supplierRepository::save);
    }
    @Override
    public Supplier findRandomSupplier(){
        Random random = new Random();

        long size = supplierRepository.count();

        long randomId = random.nextLong(1, size + 1);

        return supplierRepository.findById(randomId).orElse(null);
    }
}
