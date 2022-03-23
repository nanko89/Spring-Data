package com.example.carDealer.reposiitory;

import com.example.carDealer.model.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s.carId.make, s.carId.model, s.carId.travelledDistance," +
            " s.customerId.name, s.discount, " +
            "(SELECT SUM(p.price) FROM Part p JOIN Car c2 WHERE c2.id = s.carId.id)" +
            " FROM Sale s")
    List<Sale> findAllSaleAndDistinct();
}
