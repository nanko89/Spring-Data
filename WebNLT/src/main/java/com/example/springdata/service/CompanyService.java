package com.example.springdata.service;

import com.example.springdata.dto.xml.CompanyDto;
import com.example.springdata.entity.Company;

import java.io.IOException;

public interface CompanyService {

    String FILE_PATH = "files/xmls/companies.xml";

    boolean exists();

    String getXmlForImport() throws IOException;

    Long create(CompanyDto request);

    Company find(Long id);
}
