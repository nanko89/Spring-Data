package com.example.springdata.service.impl;

import com.example.springdata.dto.exportDto.ExportedProjectDto;
import com.example.springdata.dto.xml.ProjectDto;
import com.example.springdata.entity.Company;
import com.example.springdata.entity.Project;
import com.example.springdata.repository.ProjectRepository;
import com.example.springdata.service.CompanyService;
import com.example.springdata.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    @Autowired
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

        Project existing = this.projectRepository.findFirstByNameAndCompanyName(request.getName(),
                request.getCompany().getName());
        if (existing != null) {
            return existing.getId();
        }

        Long companyId = this.companyService.create(request.getCompany());

        Company company = this.companyService.find(companyId);

        Project project = this.modelMapper.map(request, Project.class);

        project.setCompany(company);

        this.projectRepository.save(project);

        return project.getId();
    }

    @Override
    public Project findById(Long id) {
        return this.projectRepository.findById(id).orElseThrow();
    }

    @Override
    public List<ExportedProjectDto> finishedProjects() {
        return this.projectRepository
                .findAll()
                .stream()
                .filter(Project::isFinished)
                .map(p -> this.modelMapper.map(p, ExportedProjectDto.class))
                .collect(Collectors.toList());
    }
}
