package com.example.productShop.service.impl;

import com.example.productShop.model.dto.category.CategoriesViewRootDTO;
import com.example.productShop.model.dto.category.CategoryCountProductDTO;
import com.example.productShop.model.dto.category.CategorySeedDTO;
import com.example.productShop.model.entity.Category;
import com.example.productShop.reposiitory.CategoryRepository;
import com.example.productShop.service.CategoryService;
import com.example.productShop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public long getEntityCount() {
        return categoryRepository.count();
    }

    @Override
    public Set<Category> getRandomCategory() {
        Set<Category> categories = new HashSet<>();
        Random random = new Random();

        int count = random.nextInt(4);

        long size = categoryRepository.count();

        for (int i = 0; i < count; i++) {
            Long randomId = random.nextLong(1, size);

            categoryRepository.findById(randomId).ifPresent(categories::add);

        }

        return categories;
    }

    @Override
    public CategoriesViewRootDTO findAllCategoryOrderBYCountOfProducts() {

        CategoriesViewRootDTO categoriesViewRootDTO = new CategoriesViewRootDTO();

        categoriesViewRootDTO
                .setCategories(categoryRepository
                        .findAllCategoryOrderByNumberOfProduct());
        return categoriesViewRootDTO;
    }

    @Override
    public void seedCategories(List<CategorySeedDTO> categories) {

        categories
                .stream()
                .filter(validationUtil::isValid)
                .map(categorySeedDTO -> modelMapper.map(categorySeedDTO, Category.class))
                .forEach(categoryRepository::save);
    }

}
