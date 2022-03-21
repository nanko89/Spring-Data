package com.example.carDealer;

import com.example.carDealer.models.dto.CarInfoDTO;
import com.example.carDealer.models.dto.CarMakeDTO;
import com.example.carDealer.models.dto.CustomersOrderedDTO;
import com.example.carDealer.models.dto.SupplierLocalDTO;
import com.example.carDealer.service.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Component
public class CarDealerRunnerImpl implements CommandLineRunner {

    private final static String OUTPUT_PATH = "src/main/resources/Files/out/";
    private final static String ORDER_CUSTOMERS_PATH = "ordered-customers.json";
    private final static String TOYOTA_CARS_PATH = "toyota-cars.json";
    private final static String LOCAL_SUPPLIER_PATH = "local-suppliers.json";
    private final static String CAR_AND_PARTS_PATH = "car-and-parts.json";

    private final CarService carService;
    private final CustomerService customerService;
    private final PartService partService;
    private final SupplierService supplierService;
    private final SaleService saleService;
    private final BufferedReader reader;
    private final Gson gson;

    @Autowired
    public CarDealerRunnerImpl(CarService carService, CustomerService customerService, PartService partService, SupplierService supplierService, SaleService saleService, Gson gson) {
        this.carService = carService;
        this.customerService = customerService;
        this.partService = partService;
        this.supplierService = supplierService;
        this.saleService = saleService;
        this.gson = gson;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        System.out.println("Enter query number from car dealer exercise:");
        int exNumber = Integer.parseInt(reader.readLine());

        switch (exNumber) {
            case 1 -> orderedCustomers();
            case 2 -> carsFromMakeToyota();
            case 3 -> localSuppliers();
            case 4 -> carsWithListOfParts();

        }
    }

    private void carsWithListOfParts() throws IOException {
        List<CarInfoDTO> carInfoDTOS = carService
                .findAllCarWithTheirParts();

        String content = gson.toJson(carInfoDTOS);

        writeToFile(OUTPUT_PATH + CAR_AND_PARTS_PATH, content);
    }

    private void localSuppliers() throws IOException {
        List<SupplierLocalDTO> supplierLocalDTO = supplierService
                .findAllLocalSupplierAndNumberOfParts();

        String content = gson.toJson(supplierLocalDTO);

        writeToFile(OUTPUT_PATH + LOCAL_SUPPLIER_PATH, content);
    }

    private void carsFromMakeToyota() throws IOException {
        List<CarMakeDTO> carMakeDTO = carService
                .findCarMakeToyotaOrderByModelAscAndTravelDistance();

        String content = gson.toJson(carMakeDTO);

        writeToFile(OUTPUT_PATH + TOYOTA_CARS_PATH, content);
    }

    private void orderedCustomers() throws IOException {
        List<CustomersOrderedDTO> customersOrderedDTOS = customerService
                .findAllCustomersOrderByBirthdateAndIsDriverYounger();

        String content = gson.toJson(customersOrderedDTOS);

        writeToFile(OUTPUT_PATH + ORDER_CUSTOMERS_PATH, content);
    }

    private void writeToFile(String path, String content) throws IOException {
        Files.write(Path.of(path), Collections.singleton(content));
    }

    private void seedData() throws IOException {
        supplierService.seedSupplierData();
        partService.seedPartData();
        carService.seedCarData();
        customerService.seedCustomerData();
        saleService.fillData();
    }
}
