package com.example.advquerying.service;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class IngredientServiceImpl implements IngredientService{

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> selectIngredientsByName(String symbols) {
        return ingredientRepository.findAllByNameStartingWith(symbols);
    }

    @Override
    public List<Ingredient> selectIngredientsInNames(List<String> names) {
        return ingredientRepository.findAllByNameInOrderByPriceAsc(names);
    }

    @Override
    @Transactional
    public int deleteByName(String name) {
        return ingredientRepository.deleteByName(name);
    }

    @Override
    @Transactional
    public void increasePriceByPercentage(double percent) {
        BigDecimal actualPercent = BigDecimal.valueOf(percent + 1);
        this.ingredientRepository.increasePriceByPercent(actualPercent);
    }

    @Override
    public void increasePriceByGivenNames(double percent, Set<String> names) {
        BigDecimal actualPercent = BigDecimal.valueOf(percent + 1);
        this.ingredientRepository.increasePriceByGivenName(actualPercent, names);

    }
}
