package com.example.carDealer.service.impl;

import com.example.carDealer.models.entity.Discount;
import com.example.carDealer.models.entity.Sale;
import com.example.carDealer.repository.SaleRepository;
import com.example.carDealer.service.CarService;
import com.example.carDealer.service.CustomerService;
import com.example.carDealer.service.SaleService;
import com.example.carDealer.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final CarService carService;
    private final CustomerService customerService;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, CarService carService, CustomerService customerService) {
        this.saleRepository = saleRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.carService = carService;
        this.customerService = customerService;
    }

    @Override
    public void fillData() {
        if (saleRepository.count() > 0){
            return;
        }
        Random random = new Random();

        for (int i = 0; i < 30; i++) {
            Sale sale = new Sale();

            sale.setCustomerId(customerService.findRandomCustomer());
            int randomId = random.nextInt(1,8);
            sale.setDiscount(Discount.values()[randomId]);
            sale.setCarId(carService.findRandomCar());
            saleRepository.save(sale);
        }
    }
}
