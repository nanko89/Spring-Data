package com.example.carDealer.service.impl;

import com.example.carDealer.models.dto.CarMakeDTO;
import com.example.carDealer.models.dto.CustomersOrderedDTO;
import com.example.carDealer.models.dto.seed.CustomerSeedDTO;
import com.example.carDealer.models.entity.Customer;
import com.example.carDealer.repository.CustomerRepository;
import com.example.carDealer.service.CustomerService;
import com.example.carDealer.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.customerRepository = customerRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCustomerData() throws IOException {
        if (customerRepository.count() > 0){
            return;
        }

        String PATH_CUSTOMER = "src/main/resources/Files/car.dealer/customers.json";

        String readString = Files.readString(Path.of(PATH_CUSTOMER));

        Arrays.stream(gson.fromJson(readString, CustomerSeedDTO[].class))
                .filter(validationUtil::isValid)
                .map(customerSeedDTO -> modelMapper.map(customerSeedDTO, Customer.class))
                .forEach(customerRepository::save);
    }

    @Override
    public Customer findRandomCustomer() {
        Random random = new Random();
        long randomId = random.nextLong(1,customerRepository.count()+1);

        return customerRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<CustomersOrderedDTO> findAllCustomersOrderByBirthdateAndIsDriverYounger() {
       return customerRepository
                .findAllCustomersOrderByBirthdateAndIsYoungerDriver()
                .stream()
                .map(customer -> modelMapper.map(customer,CustomersOrderedDTO.class))
                .collect(Collectors.toList());

    }
}
