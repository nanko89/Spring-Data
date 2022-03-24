package com.example.carDealer;

import com.example.carDealer.model.dto.car.CarSeedRootDTO;
import com.example.carDealer.model.dto.car.CarViewRootToyotaDTO;
import com.example.carDealer.model.dto.car.CarViewRootWithPartsDTO;
import com.example.carDealer.model.dto.customer.CustomerSeedRootDTO;
import com.example.carDealer.model.dto.customer.CustomerViewRootDTO;
import com.example.carDealer.model.dto.customer.CustomerViewRootTotalSaleDTO;
import com.example.carDealer.model.dto.part.PartSeedRootDTO;
import com.example.carDealer.model.dto.supplier.SupplierSeedRootDTO;
import com.example.carDealer.model.dto.supplier.SupplierViewRootDTO;
import com.example.carDealer.service.*;
import com.example.carDealer.util.XmlParse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

@Component
public class CarDealerRunner implements CommandLineRunner {

    public static final String PATH_OF_FILES = "src/main/resources/Files/car.dealer/";
    public static final String CAR_PATH = "cars.xml";
    public static final String SUPPLIER_PATH = "suppliers.xml";
    public static final String PART_PATH = "parts.xml";
    public static final String CUSTOMER_PATH = "customers.xml";
    public static final String OUT_PATH = "src/main/resources/Files/out/";
    public static final String ORDERED_CUSTOMERS = "ordered-customers.xml";
    private final static String TOYOTA_CARS_PATH = "toyota-cars.xml";
    private final static String LOCAL_SUPPLIER_PATH = "local-suppliers.xml";
    private final static String CAR_AND_PARTS_PATH = "car-and-parts.xml";
    private final static String CUSTOMERS_TOTAL_SALES_PATH = "customers-total-sales.xml";

    private final CarService carService;
    private final CustomerService customerService;
    private final PartService partService;
    private final SupplierService supplierService;
    private final SaleService saleService;
    private final XmlParse xmlParse;
    private final BufferedReader bufferedReader;

    @Autowired
    public CarDealerRunner(CarService carService, CustomerService customerService, PartService partService, SupplierService supplierService, SaleService saleService, XmlParse xmlParse) {
        this.carService = carService;
        this.customerService = customerService;
        this.partService = partService;
        this.supplierService = supplierService;
        this.saleService = saleService;
        this.xmlParse = xmlParse;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        while (true) {
            System.out.println("Enter query number from car dealer exercise:");
            int exNumber = Integer.parseInt(bufferedReader.readLine());

            switch (exNumber) {
                case 1 -> orderedCustomers();
                case 2 -> carsFromMakeToyota();
                case 3 -> localSuppliers();
                case 4 -> carsWithListOfParts();
                case 5 -> totalSalesByCustomer();
                default -> System.out.println("Not valid query number! Try again!");
            }
        }
    }

    private void seedData() throws JAXBException, FileNotFoundException {

        if (supplierService.getEntityCount() == 0) {
            SupplierSeedRootDTO supplierSeedRootDTO = xmlParse
                    .fromFile(PATH_OF_FILES + SUPPLIER_PATH,
                            SupplierSeedRootDTO.class);

            supplierService
                    .seedSupplier(supplierSeedRootDTO.getSuppliers());
        }

        if (partService.getEntityCount() == 0) {
            PartSeedRootDTO partSeedRootDTO = xmlParse
                    .fromFile(PATH_OF_FILES + PART_PATH,
                            PartSeedRootDTO.class);

            partService
                    .seedPart(partSeedRootDTO.getParts());
        }

        if (carService.getEntityCount() == 0) {
            CarSeedRootDTO carSeedRootDTO = xmlParse
                    .fromFile(PATH_OF_FILES + CAR_PATH,
                            CarSeedRootDTO.class);
            carService
                    .seedCar(carSeedRootDTO.getCars());
        }

        if (customerService.getEntityCount() == 0) {
            CustomerSeedRootDTO customerSeedRootDTO = xmlParse
                    .fromFile(PATH_OF_FILES + CUSTOMER_PATH,
                            CustomerSeedRootDTO.class);

            customerService
                    .seedCustomer(customerSeedRootDTO.getCustomers());
        }

        if (saleService.getEntityCount() == 0) {
            saleService.seedSale();
        }

    }

    private void orderedCustomers() throws JAXBException {
        CustomerViewRootDTO customerViewRootDTO = customerService
                .showAllCustomersOrderByBirthDateAndIsYoungDriver();

        xmlParse
                .writeToFile(OUT_PATH + ORDERED_CUSTOMERS,
                        customerViewRootDTO);
    }

    private void carsFromMakeToyota() throws JAXBException {
        CarViewRootToyotaDTO carViewRootToyotaDTO = carService
                .findAllCarToyotaOrderByModelAndTravelDistanceDesc();

        xmlParse
                .writeToFile(OUT_PATH + TOYOTA_CARS_PATH, carViewRootToyotaDTO);
    }

    private void localSuppliers() throws JAXBException {
        SupplierViewRootDTO supplierViewRootDTO = supplierService
                .findAllLocalSuppliers();

        xmlParse
                .writeToFile(OUT_PATH + LOCAL_SUPPLIER_PATH, supplierViewRootDTO);
    }

    private void carsWithListOfParts() throws JAXBException {
        CarViewRootWithPartsDTO carViewRootWithPartsDTO = carService
                .findAllCarsWithTheirParts();

        xmlParse
                .writeToFile(OUT_PATH + CAR_AND_PARTS_PATH,
                        carViewRootWithPartsDTO);
    }

    private void totalSalesByCustomer() throws JAXBException {

        CustomerViewRootTotalSaleDTO customerViewRootTotalSaleDTO = customerService
                .findAllCustomersWithBoughtMoreThenOneCar();
        xmlParse
                .writeToFile(OUT_PATH + CUSTOMERS_TOTAL_SALES_PATH, customerViewRootTotalSaleDTO);
    }
}

