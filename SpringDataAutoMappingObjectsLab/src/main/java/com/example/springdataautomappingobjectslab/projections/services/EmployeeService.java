package com.example.springdataautomappingobjectslab.projections.services;

import com.example.springdataautomappingobjectslab.projections.entitties.Employee;

import java.util.Optional;

public interface EmployeeService {
    Optional<Employee> findOneById(Long id);
    void save (Employee employee);
}
