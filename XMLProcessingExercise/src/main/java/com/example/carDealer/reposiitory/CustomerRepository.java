package com.example.carDealer.reposiitory;

import com.example.carDealer.model.dto.customer.CustomerElementDTO;
import com.example.carDealer.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c order by c.birthDate, c.isYoungDriver")
    List<Customer> findAllCustomersOrderByBirthdateAndIsDriverYounger();

}
