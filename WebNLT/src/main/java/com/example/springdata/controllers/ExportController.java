package com.example.springdata.controllers;

import com.example.springdata.service.ProjectService;
import com.example.springdata.service.util.XmlConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/export")
public class ExportController extends BaseController {

    private final ProjectService projectService;
    private final XmlConverter xmlConverter;

    public ExportController(ProjectService projectService, XmlConverter xmlConverter) {
        this.projectService = projectService;
        this.xmlConverter = xmlConverter;
    }

    @GetMapping("/project-if-finished")
    public String finishedProjects(Model model) {

        model.addAttribute("projectsIfFinished",
                this.projectService
                        .finishedProjects()
                        .stream()
                        .map(this.xmlConverter::serialize)
                        .collect(Collectors.joining("\n")));

        return "export/export-project-if-finished";
    }
}
