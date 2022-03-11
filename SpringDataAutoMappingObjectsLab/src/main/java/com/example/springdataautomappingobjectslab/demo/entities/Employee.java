package com.example.springdataautomappingobjectslab.demo.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private LocalDate birthday;
    private Address address;
    private boolean onVacation;
    private Employee manager;
    private Set<Employee> employees;

    public Employee(String firstName, String lastName, BigDecimal salary, LocalDate birthday, Address address, boolean onVacation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.birthday = birthday;
        this.address = address;
        this.onVacation = onVacation;

        this.employees = new HashSet<>();
    }

    public boolean isOnVacation() {
        return onVacation;
    }

    public void setOnVacation(boolean onVacation) {
        this.onVacation = onVacation;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployee(Employee employee){
        this.employees.add(employee);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
