package com.example.carDealer.service;

import com.example.carDealer.models.entity.Supplier;

import java.io.IOException;

public interface SupplierService {
    void seedSupplierData() throws IOException;

    Supplier findRandomSupplier();
}
