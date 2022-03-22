package com.example.productShop.service.impl;

import com.example.productShop.model.dto.product.ProductSeedDTO;
import com.example.productShop.model.dto.product.ProductViewRootDTO;
import com.example.productShop.model.dto.product.ProductWithSellerDTO;
import com.example.productShop.model.entity.Product;
import com.example.productShop.reposiitory.ProductRepository;
import com.example.productShop.service.CategoryService;
import com.example.productShop.service.ProductService;
import com.example.productShop.service.UserService;
import com.example.productShop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, ValidationUtil validationUtil, UserService userService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.categoryService = categoryService;
    }


    @Override
    public Long getEntityCount() {
        return productRepository.count();
    }

    @Override
    public void seedProduct(List<ProductSeedDTO> products) {
        products
                .stream()
                .filter(validationUtil::isValid)
                .map(this::getProduct)
                .forEach(productRepository::save);
    }

    @Override
    public ProductViewRootDTO findProductInRange500And1000WithNoBuyer() {

        ProductViewRootDTO productViewRootDTO = new ProductViewRootDTO();

        productViewRootDTO
                .setProducts(productRepository
                         .findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal.valueOf(500),BigDecimal.valueOf(1000))
                         .stream()
                         .map(p -> {
                             ProductWithSellerDTO product = modelMapper
                                     .map(p, ProductWithSellerDTO.class);
                             product
                                     .setSeller(String.format("%s %s",
                                             p.getSeller().getFirstName() == null ? "" : p.getSeller().getFirstName()
                                            ,p.getSeller().getLastName()));
                             return product;
                         })
                         .collect(Collectors.toList()));

        return productViewRootDTO;
    }


    private Product getProduct(ProductSeedDTO productSeedDTO) {
        Product product = modelMapper.map(productSeedDTO, Product.class);

        product.setSeller(userService.getRandomUser());

        if (product.getPrice().compareTo(BigDecimal.valueOf(800)) > 0) {
            product.setBuyer(userService.getRandomUser());
        }

        product.setCategories(categoryService.getRandomCategory());

        return product;
    }
}
