package com.example.springdataautomappingobjectslab;

import com.example.springdataautomappingobjectslab.projections.entitties.Employee;
import com.example.springdataautomappingobjectslab.projections.entitties.dto.EmployeeSpringDTO;
import com.example.springdataautomappingobjectslab.projections.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
//        persist();

//        Optional<Employee> managerOp = employeeService.findOneById(1L);
//        Employee manager = managerOp.get();
//        ModelMapper modelMapper = new ModelMapper();
//
//        EmployeeSpringDTO dto =  modelMapper.map(manager, EmployeeSpringDTO.class);
//        System.out.println(dto);

        employeeService.findEmployeeBornBefore(1990)
                .forEach(System.out::println);

//        ModelMapper modelMapper = new ModelMapper();
//
//        employees.stream()
//                .map(e -> modelMapper.map(e, EmployeeSpringDTO.class))
//                .forEach(System.out::println);

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
