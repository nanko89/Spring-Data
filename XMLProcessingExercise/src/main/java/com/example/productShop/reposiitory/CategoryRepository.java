package com.example.productShop.reposiitory;

import com.example.productShop.model.dto.category.CategoryCountProductDTO;
import com.example.productShop.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT new com.example.productShop.model.dto.category.CategoryCountProductDTO (" +
            "c.name, count (p), AVG(p.price), SUM(p.price)) " +
            "FROM Product p " +
            "JOIN p.categories c " +
            "GROUP BY c.name " +
            "ORDER BY count(p) DESC")
    List<CategoryCountProductDTO> findAllCategoryOrderByNumberOfProduct();
}
