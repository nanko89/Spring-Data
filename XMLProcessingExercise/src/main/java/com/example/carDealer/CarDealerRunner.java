package com.example.carDealer;

import com.example.carDealer.model.dto.car.CarSeedRootDTO;
import com.example.carDealer.model.dto.customer.CustomerSeedRootDTO;
import com.example.carDealer.model.dto.part.PartSeedRootDTO;
import com.example.carDealer.model.dto.supplier.SupplierSeedRootDTO;
import com.example.carDealer.service.CarService;
import com.example.carDealer.service.CustomerService;
import com.example.carDealer.service.PartService;
import com.example.carDealer.service.SupplierService;
import com.example.carDealer.util.XmlParse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

@Component
public class CarDealerRunner implements CommandLineRunner {

    public static final String PATH_OF_FILES = "src/main/resources/Files/car.dealer/";
    public static final String CAR_PATH = "cars.xml";
    public static final String SUPPLIER_PATH = "suppliers.xml";
    public static final String PART_PATH = "parts.xml";
    public static final String CUSTOMER_PATH = "customers.xml";

    private final CarService carService;
    private final CustomerService customerService;
    private final PartService partService;
    private final SupplierService supplierService;
    private final XmlParse xmlParse;

    @Autowired
    public CarDealerRunner(CarService carService, CustomerService customerService, PartService partService, SupplierService supplierService, XmlParse xmlParse) {
        this.carService = carService;
        this.customerService = customerService;
        this.partService = partService;
        this.supplierService = supplierService;
        this.xmlParse = xmlParse;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
    }

    private void seedData() throws JAXBException, FileNotFoundException {

        if (supplierService.getEntityCount() == 0){
            SupplierSeedRootDTO supplierSeedRootDTO = xmlParse
                    .fromFile(PATH_OF_FILES + SUPPLIER_PATH,
                            SupplierSeedRootDTO.class);

            supplierService
                    .seedSupplier(supplierSeedRootDTO.getSuppliers());
        }

        if (partService.getEntityCount() == 0){
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

        if (customerService.getEntityCount() == 0){
            CustomerSeedRootDTO customerSeedRootDTO = xmlParse
                    .fromFile(PATH_OF_FILES + CUSTOMER_PATH,
                            CustomerSeedRootDTO.class);

            customerService
                    .seedCustomer(customerSeedRootDTO.getCustomers());
        }



    }
}
