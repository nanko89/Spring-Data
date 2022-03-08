package com.example.advquerying.repository;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAllByNameStartingWith(String letter);

    List<Ingredient> findAllByNameInOrderByPriceAsc(List<String> ingredients);

    int deleteByName(String name);

    @Modifying
    @Query("UPDATE Ingredient As i SET i.price = i.price * :multiplayer")
    void increasePriceByPercent(@Param("multiplayer") BigDecimal percent);

    @Modifying
    @Query("UPDATE Ingredient As i SET i.price = i.price * :multiplayer " +
            "WHERE i.name IN :names")
    void increasePriceByGivenName(BigDecimal multiplayer, Set<String> names);
}
