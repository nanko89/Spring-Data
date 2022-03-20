package com.example.jsonexercisecardealer.service.impl;

import com.example.jsonexercisecardealer.models.dto.seed.CustomerSeedDTO;
import com.example.jsonexercisecardealer.models.entity.Customer;
import com.example.jsonexercisecardealer.repository.CustomerRepository;
import com.example.jsonexercisecardealer.service.CustomerService;
import com.example.jsonexercisecardealer.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

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

        String PATH_CUSTOMER = "src/main/resources/Files/customers.json";

        String readString = Files.readString(Path.of(PATH_CUSTOMER));

        Arrays.stream(gson.fromJson(readString, CustomerSeedDTO[].class))
                .filter(validationUtil::isValid)
                .map(customerSeedDTO -> modelMapper.map(customerSeedDTO, Customer.class))
                .forEach(customerRepository::save);
    }

    @Override
    public Long findRandomCustomerId() {
        long randomId = ThreadLocalRandom
                .current()
                .nextLong(1,customerRepository.count()+1);
        return randomId;
    }
}
