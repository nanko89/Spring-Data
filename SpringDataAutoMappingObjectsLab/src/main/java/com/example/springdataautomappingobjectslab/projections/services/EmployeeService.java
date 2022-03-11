package com.example.springdataautomappingobjectslab.projections.services;

import com.example.springdataautomappingobjectslab.projections.entitties.Employee;
import com.example.springdataautomappingobjectslab.projections.entitties.dto.EmployeeSpringDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Optional<Employee> findOneById(Long id);
    void save (Employee employee);

    List<EmployeeSpringDTO> findEmployeeBornBefore(int year);
}
