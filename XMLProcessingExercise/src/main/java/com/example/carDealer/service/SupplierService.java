package com.example.carDealer.service;

import com.example.carDealer.model.dto.supplier.SupplierSeedDTO;
import com.example.carDealer.model.dto.supplier.SupplierViewRootDTO;
import com.example.carDealer.model.entity.Supplier;

import java.util.List;

public interface SupplierService {
    long getEntityCount();

    void seedSupplier(List<SupplierSeedDTO> suppliers);

    Supplier findRandomSupplier();

    SupplierViewRootDTO findAllLocalSuppliers();
}
