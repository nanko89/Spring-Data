package com.example.productStore;

import com.example.productStore.model.dto.*;
import com.example.productStore.service.CategoryService;
import com.example.productStore.service.ProductService;
import com.example.productStore.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Component
public class ProductShopRunner implements CommandLineRunner {

    private final static String OUTPUT_PATH = "src/main/resources/Files/out/";
    private final static String PRODUCT_IN_RANGE = "products-in-range.json";
    private final static String USERS_SOLD_PRODUCT = "users-sold-products.json";
    private final static String CATEGORIES_BY_PRODUCT ="categories-by-product.json";
    private final static String USERS_AND_PRODUCTS ="users-and-products.json";


    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final BufferedReader reader;
    private final Gson gson;

    @Autowired
    public ProductShopRunner(CategoryService categoryService, UserService userService, ProductService productService, Gson gson) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.gson = gson;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        System.out.println("Enter query number from product shop:");
        int exNumber = Integer.parseInt(reader.readLine());

        switch (exNumber) {
            case 1 -> productInRange();
            case 2 -> successfullySoldProduct();
            case 3 -> categoryByProduct();
            case 4 -> usersAndProducts();
        }
    }

    private void usersAndProducts() throws IOException {
        List<UserSellerDTO> usersCountDTO = userService
                .countAllUsersWithMoreThenOneSoldProduct();

        String contest = gson.toJson(usersCountDTO);

        writeToFile(OUTPUT_PATH + USERS_AND_PRODUCTS, contest);
    }

    private void categoryByProduct() throws IOException {
        List<CategoryStatsDTO> categoryStatsDTO = categoryService
                .categoryByProduct();

        String contest = gson.toJson(categoryStatsDTO);

        writeToFile(OUTPUT_PATH + CATEGORIES_BY_PRODUCT, contest);
    }

    private void successfullySoldProduct() throws IOException {
        List<UserSoldDTO> userSoldDTOS = userService
                .findAllUserWithMoreThanOneSoldProduct();

        String contest = gson.toJson(userSoldDTOS);

        writeToFile(OUTPUT_PATH + USERS_SOLD_PRODUCT, contest);
    }

    private void productInRange() throws IOException {
        List<ProductNameAndPriceDTO> products = productService
                .findAllProductsInRangeOrderByPrice(BigDecimal.valueOf(500L), BigDecimal.valueOf(1000L));

        String content = gson.toJson(products);

        writeToFile(OUTPUT_PATH + PRODUCT_IN_RANGE, content);
    }

    private void writeToFile(String path, String content) throws IOException {

        Files.write(Path.of(path), Collections.singleton( content));
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        userService.seedUsers();
        productService.seedProducts();
    }
}
