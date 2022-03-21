package com.example.carDealer.service;
import com.example.carDealer.models.dto.CustomersOrderedDTO;
import com.example.carDealer.models.dto.CustomersTotalSaleDTO;
import com.example.carDealer.models.entity.Customer;
import org.springframework.data.jpa.repository.Query;

import java.io.IOException;
import java.util.List;

public interface CustomerService {
    void seedCustomerData() throws IOException;

    Customer findRandomCustomer();

    List<CustomersOrderedDTO> findAllCustomersOrderByBirthdateAndIsDriverYounger();

   //ToDo:Not finished
//    List<CustomersTotalSaleDTO> findAllCustomersOrderByTotalMoneySpend();
}
