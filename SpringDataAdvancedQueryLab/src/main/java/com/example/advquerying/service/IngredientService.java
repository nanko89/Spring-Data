package com.example.advquerying.service;

import com.example.advquerying.entities.Ingredient;

import java.util.List;
import java.util.Set;

public interface IngredientService {
    List<Ingredient> selectIngredientsByName(String symbols);

    List<Ingredient> selectIngredientsInNames(List<String> names);

    int deleteByName(String name);

    void increasePriceByPercentage(double percent);

    void increasePriceByGivenNames(double percent, Set<String> names);
}
