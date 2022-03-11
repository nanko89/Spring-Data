package com.example.springdataautomappingobjectslab.demo.dto;

import java.util.Set;
import java.util.stream.Collectors;

public class ManagerDTO {
    private String firstName;
    private String lastName;
    private Set<EmployeeDTO> employees;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeDTO> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        String printEmployee = employees.stream()
                .map(e->String.format("     - %s %s %.2f", e.getFirstName(), e.getLastName(), e.getSalary())).collect(Collectors.joining("\n"));

        return String.format("%s %s | Employees: %d%n%s%n",
                firstName,
                lastName,
                employees.size(), printEmployee);
    }
}
