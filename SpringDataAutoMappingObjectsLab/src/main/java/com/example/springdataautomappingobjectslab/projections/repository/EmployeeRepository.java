package com.example.springdataautomappingobjectslab.projections.repository;

import com.example.springdataautomappingobjectslab.projections.entitties.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
