package com.example.springdataautomappingobjectslab.demo;

import com.example.springdataautomappingobjectslab.demo.dto.EmployeeDTO;
import com.example.springdataautomappingobjectslab.demo.dto.ManagerDTO;
import com.example.springdataautomappingobjectslab.demo.entities.Address;
import com.example.springdataautomappingobjectslab.demo.entities.Employee;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MapperMain {
    public static void main(String[] args) {

        ModelMapper modelMapper = new ModelMapper();

        Address address =  new Address("street",
                "10",
                "Sofia",
                "Bulgaria");

        Employee first = new Employee("first",
                "last",
                BigDecimal.valueOf(5),
                LocalDate.now(),address
               , true);

        Employee second = new Employee("second",
                "last",
                BigDecimal.ONE,
                LocalDate.now(),address
                , true);

        Employee manager = new Employee("manager",
                "last",
                BigDecimal.TEN,
                LocalDate.now(),address
                , true);


        manager.addEmployee(first);
        manager.addEmployee(second);

        ManagerDTO managerDTO = modelMapper.map(manager, ManagerDTO.class);

        System.out.println(managerDTO);
    }
}
