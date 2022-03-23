package com.example.carDealer.reposiitory;

import com.example.carDealer.model.dto.customer.CustomerTotalSaleDTO;
import com.example.carDealer.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c order by c.birthDate, c.isYoungDriver")
    List<Customer> findAllCustomersOrderByBirthdateAndIsDriverYounger();

    @Query(value = "SELECT cu.name, " +
            "       (SELECT Count(s.id) FROM sales s WHERE s.customer_id_id = cu.id) AS bought_car , " +
            "       (SELECT SUM(p2.price) " +
            "        FROM parts p2 " +
            "                 JOIN cars_parts cp on p2.id = cp.parts_id " +
            "                 JOIN cars c2 on c2.id = cp.car_id " +
            "        WHERE cp.car_id = c2.id AND cp.car_id = c.id " +
            "        GROUP BY cp.car_id) AS spent_money " +
            "FROM customers cu " +
            "JOIN sales s2 on cu.id = s2.customer_id_id " +
            "JOIN cars c on c.id = s2.car_id_id " +
            "ORDER BY spent_money DESC, bought_car DESC", nativeQuery = true)
    List<Tuple> findAllCustomersWithBoughtMoreThenOneCar();
}
