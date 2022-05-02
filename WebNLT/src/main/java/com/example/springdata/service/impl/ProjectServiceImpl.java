package com.example.springdata.service.impl;

import com.example.springdata.dto.xml.ProjectDto;
import com.example.springdata.entity.Company;
import com.example.springdata.entity.Project;
import com.example.springdata.repository.ProjectRepository;
import com.example.springdata.service.CompanyService;
import com.example.springdata.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, CompanyService companyService,
                              ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.companyService = companyService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean exists() {
        return projectRepository.existsAllBy();
    }

    @Override
    public String getXmlForImport() throws IOException {
        return new String(this.getClass()
                .getClassLoader()
                .getResourceAsStream(FILE_PATH)
                .readAllBytes(), StandardCharsets.UTF_8);
    }

    @Override
    public Long create(ProjectDto request) {

        Project existing = this.projectRepository.findFirstByNameAndCompany(request.getName(),
                request.getCompany().getName());
        if(existing != null){
            return existing.getId();
        }

        Long companyId = this.companyService.create(request.getCompany());

        Company company = this.companyService.find(companyId);

        Project project = this.modelMapper.map(request, Project.class);

        project.setCompany(company);

        return project.getId();
    }

    @Override
    public Project findById(Long id) {
        return this.projectRepository.findById(id).orElseThrow();
    }
}
