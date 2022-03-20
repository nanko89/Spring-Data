package com.example.jsonexercisecardealer.service;

import com.example.jsonexercisecardealer.models.entity.Supplier;

import java.io.IOException;

public interface SupplierService {
    void seedSupplierData() throws IOException;

    Supplier findRandomSupplier();
}
