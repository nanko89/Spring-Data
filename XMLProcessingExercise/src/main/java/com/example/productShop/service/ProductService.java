package com.example.productShop.service;

import com.example.productShop.model.dto.product.ProductSeedDTO;
import com.example.productShop.model.dto.product.ProductViewRootDTO;

import java.util.List;

public interface ProductService {
    Long getEntityCount();

    void seedProduct(List<ProductSeedDTO> products);

    ProductViewRootDTO findProductInRange500And1000WithNoBuyer();
}
