package com.example.springdata.dto.exportDto;

public class ExportedEmployeesDto {

    private String firstName;
    private String lastName;
    private Integer age;
    private String projectName;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return String.format("Name: %s %s%n" +
                "   Age: %d%n" +
                "   Project name: %s",
                this.getFirstName(), this.getLastName(),
                this.getAge(), this.projectName);
    }
}
