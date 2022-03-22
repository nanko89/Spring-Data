package com.example.productShop.service;

import com.example.productShop.model.dto.category.CategoriesViewRootDTO;
import com.example.productShop.model.dto.category.CategorySeedDTO;
import com.example.productShop.model.entity.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories(List<CategorySeedDTO> categories);
    long getEntityCount();
    Set<Category> getRandomCategory();

    CategoriesViewRootDTO findAllCategoryOrderBYCountOfProducts();
}
