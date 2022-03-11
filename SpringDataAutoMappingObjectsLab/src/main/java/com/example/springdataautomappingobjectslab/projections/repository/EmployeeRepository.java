package com.example.springdataautomappingobjectslab.projections.repository;

import com.example.springdataautomappingobjectslab.projections.entitties.Employee;
import com.example.springdataautomappingobjectslab.projections.entitties.dto.EmployeeSpringDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<EmployeeSpringDTO> findByBirthdayIsBeforeOrderBySalaryDesc(LocalDate birthday);
}
