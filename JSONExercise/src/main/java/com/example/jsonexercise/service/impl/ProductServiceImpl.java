package com.example.jsonexercise.service.impl;

import com.example.jsonexercise.model.dto.ProductNameAndPriceDTO;
import com.example.jsonexercise.model.dto.ProductSeedDTO;
import com.example.jsonexercise.model.entity.Product;
import com.example.jsonexercise.model.entity.User;
import com.example.jsonexercise.repository.ProductRepository;
import com.example.jsonexercise.service.CategoryService;
import com.example.jsonexercise.service.ProductService;
import com.example.jsonexercise.service.UserService;
import com.example.jsonexercise.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

        private final ProductRepository productRepository;
        private final UserService userService;
        private final CategoryService categoryService;
        private final ModelMapper modelMapper;
        private final ValidationUtil validationUtil;
        private final Gson gson;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, CategoryService categoryService, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }


    @Override
    public void seedProducts() throws IOException {

        if (productRepository.count() > 0){
            return;
        }

        String PATH_PRODUCT = "src/main/resources/Files/products.json";
        
        String fileContent = Files.readString(Path.of(PATH_PRODUCT));

        Arrays.stream(gson.fromJson(fileContent, ProductSeedDTO[].class))
                .filter(validationUtil::isValid)
                .map(productSeedDTO -> {
                    return getProduct(productSeedDTO);
                })
                .forEach(productRepository::save);

    }

    @Override
    public List<ProductNameAndPriceDTO> findAllProductsInRangeOrderByPrice(BigDecimal lowerRange, BigDecimal upperRange) {
        return productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(lowerRange,upperRange)
                .stream()
                .map(product ->{
                    ProductNameAndPriceDTO productNameAndPriceDTO = modelMapper
                            .map(product, ProductNameAndPriceDTO.class);
                    productNameAndPriceDTO.setSeller(String.format("%s %s",
                            product.getSeller().getFirstName(),
                            product.getSeller().getLastName()));

                    return productNameAndPriceDTO;
                }).collect(Collectors.toList());
    }


    private Product getProduct(ProductSeedDTO productSeedDTO) {
        Product product =  modelMapper.map(productSeedDTO, Product.class);
        User seller = userService.findRandomUser();
        User buyer = userService.findRandomUser();
        product.setSeller(seller);
        if (product.getPrice().compareTo(BigDecimal.valueOf(850L)) < 0){
            if (Objects.equals(seller.getId(), buyer.getId())){
                buyer = userService.findRandomUser();
            }
            product.setBuyer(userService.findRandomUser());
        }
        product.setCategories(categoryService.findRandomCategory());
        return product;
    }
}
