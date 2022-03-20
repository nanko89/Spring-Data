package com.example.productStore.service.impl;

import com.example.productStore.model.dto.CategorySeedDTO;
import com.example.productStore.model.dto.CategoryStatsDTO;
import com.example.productStore.model.entity.Category;
import com.example.productStore.repository.CategoryRepository;
import com.example.productStore.service.CategoryService;
import com.example.productStore.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() > 0 ){
            return;
        }

        String PATH_CATEGORY = "src/main/resources/Files/product.store/categories.json";
        Arrays.stream(gson.fromJson(Files.readString(Path.of(PATH_CATEGORY)), CategorySeedDTO[].class))
                .filter(validationUtil::isValid)
                .map(categorySeedDTO -> modelMapper.map(categorySeedDTO, Category.class))
                .forEach(categoryRepository::save);

    }

    @Override
    public Set<Category> findRandomCategory() {
        Set<Category> categories = new HashSet<>();

        int categoryCount = ThreadLocalRandom
                .current()
                .nextInt(1,4);

        for (int i = 0; i < categoryCount; i++) {

        long randomId = ThreadLocalRandom
                .current()
                .nextLong(1,categoryRepository.count()+1);

        categories
                .add(categoryRepository.findById(randomId)
                        .orElse(null));
        }
        return categories;
    }

    @Override
    public List<CategoryStatsDTO> categoryByProduct() {
        return categoryRepository.categoriesByProductCount();
    }
}
