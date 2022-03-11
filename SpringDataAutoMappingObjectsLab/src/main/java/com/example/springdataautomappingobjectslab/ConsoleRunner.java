package com.example.springdataautomappingobjectslab;

import com.example.springdataautomappingobjectslab.projections.entitties.Employee;
import com.example.springdataautomappingobjectslab.projections.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private EmployeeService employeeService;

    @Autowired
    public ConsoleRunner(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
        persist();

        Optional<Employee> managerOp = employeeService.findOneById(1L);
        Employee manager = managerOp.get();

    }

    private void persist() {
        Employee manager = new Employee
                ("Steve",
                        "Jobbsen",
                        BigDecimal.valueOf(6000.20),
                        LocalDate.now(),
                        null);

        Employee employee1 = new Employee
                ("Kirilyc",
                        "Lefi ",
                        BigDecimal.valueOf(4400.00 ),
                        LocalDate.now(),
                        manager);


        Employee employee2 = new Employee
                ("Stephen ",
                        "Bjorn  ",
                        BigDecimal.valueOf(4300.00 ),
                        LocalDate.now(),
                        manager);


        this.employeeService.save(employee1);
//        this.employeeService.save(employee2);
    }
}
