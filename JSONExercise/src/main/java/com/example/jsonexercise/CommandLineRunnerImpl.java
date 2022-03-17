package com.example.jsonexercise;

import com.example.jsonexercise.model.dto.ProductNameAndPriceDTO;
import com.example.jsonexercise.service.CategoryService;
import com.example.jsonexercise.service.ProductService;
import com.example.jsonexercise.service.UserService;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final static String OUTPUT_PATH = "src/main/resources/Files/out/";
    private final static String PRODUCT_IN_RANGE = "products-in-range.json";

    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final BufferedReader reader;
    private final Gson gson;


    public CommandLineRunnerImpl(CategoryService categoryService, UserService userService, ProductService productService, Gson gson) {
        this.categoryService = categoryService;
        this.userService = userService;

        this.productService = productService;
        this.gson = gson;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        System.out.println("Enter exercise number:");
        int exNumber = Integer.parseInt(reader.readLine());

        switch (exNumber) {
            case 1 -> productInRange();
        }
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
