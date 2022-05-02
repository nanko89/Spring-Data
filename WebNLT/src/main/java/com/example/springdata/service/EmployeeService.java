package com.example.springdata.service;

import com.example.springdata.dto.xml.EmployeeDto;

import java.io.IOException;

public interface EmployeeService {

    String FILE_PATH = "files/xmls/employees.xml";

    boolean exists();

    String getXmlForImport() throws IOException;

    Long create(EmployeeDto request);

}
