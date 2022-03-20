package com.example.productStore.repository;

import com.example.productStore.model.dto.CategoryStatsDTO;
import com.example.productStore.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query
            ("SELECT new com.example.productStore.model.dto.CategoryStatsDTO (" +
                    "c.name, COUNT(p) , AVG(p.price),SUM(p.price))" +
                    " FROM products p " +
                    " JOIN p.categories c " +
                    " GROUP BY c")
    List<CategoryStatsDTO> categoriesByProductCount();
}
