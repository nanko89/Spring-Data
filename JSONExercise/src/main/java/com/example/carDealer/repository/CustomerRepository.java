package com.example.carDealer.repository;

import com.example.carDealer.models.dto.CustomersTotalSaleDTO;
import com.example.carDealer.models.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("SELECT c FROM Customer c ORDER BY c.birthDate, c.isYoungDriver")
    List<Customer> findAllCustomersOrderByBirthdateAndIsYoungerDriver();

//    @Query("SELECT new com.example.carDealer.models.dto.CustomersTotalSaleDTO(" +
//            "cu.name , COUNT(c.id), SUM (p.price * p.quantity))" +
//            " FROM Customer cu " +
//            " JOIN Sale s " +
//            " JOIN Car c " +
//            " JOIN Part p " +
//            " WHERE (SELECT count(s.customerId) FROM Sale s JOIN Car c3 WHERE c3.id = c.id) > 0 " +
//            " GROUP BY cu.id " +
//            " ORDER BY COUNT(c.id) DESC ,  SUM (p.price * p.quantity) DESC")
//    List<CustomersTotalSaleDTO> findAllCustomersOrderByTotalSpendMoney();
}
