package com.example.jsonexercise.service.impl;

import com.example.jsonexercise.repository.CategoryRepository;
import com.example.jsonexercise.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final String resourcePath = "src/main/resources/Files/categories.json";

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void seedCategories() throws IOException {
        String readCategories = Files.readString(Path.of(resourcePath));
    }
}
