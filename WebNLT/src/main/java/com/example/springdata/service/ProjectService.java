package com.example.springdata.service;

import com.example.springdata.dto.xml.ProjectDto;
import com.example.springdata.entity.Project;

import java.io.IOException;

public interface ProjectService {

    String FILE_PATH = "files/xmls/projects.xml";

    boolean exists();

    String getXmlForImport() throws IOException;

    Long create(ProjectDto request);

    Project findById(Long id);

}
