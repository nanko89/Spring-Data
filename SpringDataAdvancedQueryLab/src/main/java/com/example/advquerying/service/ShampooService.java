package com.example.advquerying.service;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {
    List<Shampoo> selectBySize(Size size);

    List<Shampoo> selectBySizeOrLabelId(Size size, long id);

    List<Shampoo> selectMoreExpensiveThan(BigDecimal price);


    int countShampooByPrice(BigDecimal valueOf);

    List<Shampoo> selectShampooByIngredientsCount(int count);
}
