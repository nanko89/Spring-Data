package com.example.carDealer.service;

import com.example.carDealer.model.dto.customer.CustomerSeedDTO;
import com.example.carDealer.model.entity.Customer;

import java.util.List;

public interface CustomerService {
    long getEntityCount();

    void seedCustomer(List<CustomerSeedDTO> customers);

    Customer findRandomCar();
}
