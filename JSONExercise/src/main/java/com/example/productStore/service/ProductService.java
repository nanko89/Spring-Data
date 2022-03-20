package com.example.productStore.service;

import com.example.productStore.model.dto.ProductNameAndPriceDTO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProducts() throws IOException;

    List<ProductNameAndPriceDTO> findAllProductsInRangeOrderByPrice(BigDecimal lowerRange, BigDecimal upperRange);
}
