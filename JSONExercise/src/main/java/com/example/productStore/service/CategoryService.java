package com.example.productStore.service;

import com.example.productStore.model.dto.CategoryStatsDTO;
import com.example.productStore.model.entity.Category;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> findRandomCategory();

    List<CategoryStatsDTO> categoryByProduct();
}
