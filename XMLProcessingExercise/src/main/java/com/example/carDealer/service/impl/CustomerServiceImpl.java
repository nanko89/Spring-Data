package com.example.carDealer.service.impl;

import com.example.carDealer.model.dto.customer.*;
import com.example.carDealer.model.entity.Customer;
import com.example.carDealer.reposiitory.CustomerRepository;
import com.example.carDealer.service.CustomerService;
import com.example.carDealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public long getEntityCount() {
        return customerRepository.count();
    }

    @Override
    public Customer findRandomCustomer() {
        Random random = new Random();

        long count = customerRepository.count();

        long randomId = random
                .nextLong(1, count + 1);

        return customerRepository
                .findById(randomId).orElse(null);
    }

    @Override
    public CustomerViewRootDTO showAllCustomersOrderByBirthDateAndIsYoungDriver() {
        CustomerViewRootDTO customerSeedRootDTO = new CustomerViewRootDTO();
        customerSeedRootDTO
                .setCustomers(customerRepository
                        .findAllCustomersOrderByBirthdateAndIsDriverYounger()
                        .stream()
                        .map(customer -> modelMapper.map(customer, CustomerElementDTO.class))
                        .collect(Collectors.toList())
                );

        return customerSeedRootDTO;
    }

    @Override
    public CustomerViewRootTotalSaleDTO findAllCustomersWithBoughtMoreThenOneCar() {

        CustomerViewRootTotalSaleDTO customerViewRootTotalSaleDTO =
                new CustomerViewRootTotalSaleDTO();

        List<Tuple> customerTotalTuple = customerRepository
                .findAllCustomersWithBoughtMoreThenOneCar();

        List<CustomerTotalSaleDTO> customerTotalSaleDTOS = customerTotalTuple
                .stream()
                .map(c -> new CustomerTotalSaleDTO(
                                c.get(0, String.class),
                                c.get(1, BigInteger.class),
                                c.get(2, BigDecimal.class)))
                .collect(Collectors.toList());

        customerViewRootTotalSaleDTO
                .setCustomers(customerTotalSaleDTOS);

        return customerViewRootTotalSaleDTO;
    }

    @Override
    public void seedCustomer(List<CustomerSeedDTO> customers) {
        customers
                .stream()
                .filter(validationUtil::isValid)
                .map(customerSeedDTO -> modelMapper.map(customerSeedDTO, Customer.class))
                .forEach(customerRepository::save);
    }
}
