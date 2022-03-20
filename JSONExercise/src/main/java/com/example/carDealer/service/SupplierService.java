package com.example.carDealer.service;

import com.example.carDealer.models.dto.SupplierLocalDTO;
import com.example.carDealer.models.entity.Supplier;

import java.io.IOException;
import java.util.List;

public interface SupplierService {
    void seedSupplierData() throws IOException;

    Supplier findRandomSupplier();

    List<SupplierLocalDTO> findAllLocalSupplierAndNumberOfParts();
}
