package com.example.carDealer.service;

import com.example.carDealer.model.dto.sale.SaleViewRootDTO;

public interface SaleService {
    long getEntityCount();

    void seedSale();

//    SaleViewRootDTO findAllSaleAndDiscount();
}
