package com.example.springdata.service.impl;

import com.example.springdata.dto.xml.EmployeeDto;
import com.example.springdata.entity.Employee;
import com.example.springdata.entity.Project;
import com.example.springdata.repository.EmployeeRepository;
import com.example.springdata.service.EmployeeService;
import com.example.springdata.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ProjectService projectService;

    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ProjectService projectService, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.projectService = projectService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean exists() {
        return employeeRepository.existsAllBy();
    }

    @Override
    public String getXmlForImport() throws IOException {
        return new String(this.getClass()
                .getClassLoader()
                .getResourceAsStream(FILE_PATH)
                .readAllBytes(), StandardCharsets.UTF_8);
    }

    @Override
    public Long create(EmployeeDto request) {
        Employee existing = employeeRepository.findFirstByFirstNameAndLastNameAndAge(
                request.getFirstName(),
                request.getLastName(),
                request.getAge());

        if (existing != null) {
            return existing.getId();
        }

        Employee employee = modelMapper.map(request, Employee.class);

        Long projectId = this.projectService.create(request.getProject());

        Project project = this.projectService.findById(projectId);

        employee.setProject(project);

        employeeRepository.save(employee);

        return employee.getId();
    }
}
