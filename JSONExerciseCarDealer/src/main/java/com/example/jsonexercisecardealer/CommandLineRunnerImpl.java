package com.example.jsonexercisecardealer;

import com.example.jsonexercisecardealer.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CarService carService;
    private final CustomerService customerService;
    private final PartService partService;
    private final SupplierService supplierService;
    private final SaleService saleService;

    @Autowired
    public CommandLineRunnerImpl(CarService carService, CustomerService customerService, PartService partService, SupplierService supplierService, SaleService saleService) {
        this.carService = carService;
        this.customerService = customerService;
        this.partService = partService;
        this.supplierService = supplierService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
    }

    private void seedData() throws IOException {
        supplierService.seedSupplierData();
        partService.seedPartData();
        carService.seedCarData();
        customerService.seedCustomerData();
        saleService.fillData();

    }
}
