package com.example.springdata.service;

import com.example.springdata.dto.exportDto.ExportedProjectDto;
import com.example.springdata.dto.xml.ProjectDto;
import com.example.springdata.entity.Project;

import java.io.IOException;
import java.util.List;

public interface ProjectService {

    String FILE_PATH = "files/xmls/projects.xml";

    boolean exists();

    String getXmlForImport() throws IOException;

    Long create(ProjectDto request);

    Project findById(Long id);

    List<ExportedProjectDto> finishedProjects();


}
