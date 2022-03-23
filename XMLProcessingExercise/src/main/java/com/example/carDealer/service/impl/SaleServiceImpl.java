package com.example.carDealer.service.impl;

import com.example.carDealer.model.entity.Discount;
import com.example.carDealer.model.entity.Sale;
import com.example.carDealer.reposiitory.SaleRepository;
import com.example.carDealer.service.CarService;
import com.example.carDealer.service.CustomerService;
import com.example.carDealer.service.SaleService;
import com.example.carDealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final CarService carService;
    private final CustomerService customerService;


    public SaleServiceImpl(SaleRepository saleRepository, ModelMapper modelMapper, ValidationUtil validationUtil, CarService carService, CustomerService customerService) {
        this.saleRepository = saleRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.carService = carService;
        this.customerService = customerService;
    }

    @Override
    public long getEntityCount() {
        return this.saleRepository.count();
    }

    @Override
    public void seedSale() {
            Random random = new Random();

        for (int i = 0; i < 40; i++) {
            Sale sale = new Sale();
            sale.setCarId(carService.findRandomCar());
            sale.setCustomer(customerService.findRandomCustomer());
            int randomPercent = random.nextInt(0,7);
            sale.setDiscount(Discount.values()[randomPercent]);
            saleRepository.save(sale);
        }
    }
}
