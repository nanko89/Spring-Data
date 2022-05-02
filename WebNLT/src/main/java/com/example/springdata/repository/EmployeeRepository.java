package com.example.springdata.repository;

import com.example.springdata.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsAllBy();

    Employee findFirstByFirstNameAndLastNameAndAge(String firstName, String lastName, Integer age);

}
