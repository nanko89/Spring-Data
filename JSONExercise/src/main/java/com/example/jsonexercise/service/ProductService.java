package com.example.jsonexercise.service;

import com.example.jsonexercise.model.dto.ProductNameAndPriceDTO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProducts() throws IOException;

    List<ProductNameAndPriceDTO> findAllProductsInRangeOrderByPrice(BigDecimal lowerRange, BigDecimal upperRange);
}
