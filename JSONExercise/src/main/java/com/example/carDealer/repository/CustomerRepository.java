package com.example.carDealer.repository;

import com.example.carDealer.models.dto.CustomersOrderedDTO;
import com.example.carDealer.models.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("SELECT c FROM Customer c ORDER BY c.birthDate, c.isYoungDriver")
    List<Customer> findAllCustomersOrderByBirthdateAndIsYoungerDriver();
}
