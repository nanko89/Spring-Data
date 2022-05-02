package com.example.springdata.service.impl;

import com.example.springdata.dto.xml.CompanyDto;
import com.example.springdata.entity.Company;
import com.example.springdata.repository.CompanyRepository;
import com.example.springdata.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean exists() {
        return companyRepository.existsAllBy();
    }

    @Override
    public String getXmlForImport() throws IOException {
        return new String(this.getClass()
                .getClassLoader()
                .getResourceAsStream(FILE_PATH)
                .readAllBytes(), StandardCharsets.UTF_8);
    }

    @Override
    public Long create(CompanyDto request) {

        Company existing = this.companyRepository.findFirstByName(request.getName());

        if (existing != null) {
            return existing.getId();
        }

        Company company = this.modelMapper.map(request, Company.class);

        this.companyRepository.save(company);

        return company.getId();
    }

    @Override
    public Company find(Long id) {
        return companyRepository
                .findById(id)
                .orElseThrow();
    }


}

