package com.example.springdata.controllers;

import com.example.springdata.dto.importDto.ImportCompaniesDto;
import com.example.springdata.dto.importDto.ImportEmployeeDto;
import com.example.springdata.dto.importDto.ImportProjectsDto;
import com.example.springdata.dto.xml.CompanyRootDto;
import com.example.springdata.dto.xml.EmployeesRootDto;
import com.example.springdata.dto.xml.ProjectRootDto;
import com.example.springdata.service.CompanyService;
import com.example.springdata.service.EmployeeService;
import com.example.springdata.service.ProjectService;
import com.example.springdata.service.util.XmlConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class ImportController extends BaseController {

    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;
    private final XmlConverter converter;


    public ImportController(CompanyService companyService, EmployeeService employeeService, ProjectService projectService, XmlConverter converter) {
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.projectService = projectService;
        this.converter = converter;
    }

    @GetMapping("/import/xml")
    public String importPage(HttpServletRequest request, Model model) {

        if (!this.isLogged(request)) {
            return "redirect:/";
        }

        model.addAttribute("areImported", new boolean[]
                {this.companyService.exists(), this.projectService.exists(), this.employeeService.exists()});
        return "xml/import-xml";
    }

    @GetMapping("/import/companies")
    public String importCompanies(HttpServletRequest request, Model model) throws IOException {
        if (!this.isLogged(request)) {
            return "redirect:/";
        }

        model.addAttribute("companies",
                this.companyService.getXmlForImport());
        return "xml/import-companies";
    }

    @PostMapping("/import/companies")
    public String importCompanies(HttpServletRequest httpRequest, ImportCompaniesDto request) {

        if (!this.isLogged(httpRequest)) {
            return "redirect:/";
        }

        CompanyRootDto companyRootDto = this.converter
                .deserialize(request.getCompanies(), CompanyRootDto.class);

        companyRootDto.getCompanies().forEach(this.companyService::create);

        return "redirect:/import/xml";
    }

    @GetMapping("/import/projects")
    public String importProjects(HttpServletRequest request, Model model) throws IOException {
        if (!this.isLogged(request)) {
            return "redirect:/";
        }

        model.addAttribute("projects",
                this.projectService.getXmlForImport());
        return "xml/import-projects";
    }

    @PostMapping("/import/projects")
    public String importProjects(HttpServletRequest httpRequest, ImportProjectsDto request) {
        if (!this.isLogged(httpRequest)) {
            return "redirect:/";
        }
        ProjectRootDto projectRootDto = this.converter
                .deserialize(request.getProjects(), ProjectRootDto.class);

        projectRootDto.getProjects().forEach(this.projectService::create);

        return "redirect:/import/xml";
    }

    @GetMapping("/import/employees")
    public String importEmployees(HttpServletRequest request, Model model) throws IOException {
        if (!this.isLogged(request)) {
            return "redirect:/";
        }

        model.addAttribute("employees",
                this.employeeService.getXmlForImport());
        return "xml/import-employees";
    }

    @PostMapping("/import/employees")
    public String importEmployees(HttpServletRequest httpRequest, ImportEmployeeDto request) {
        if (!this.isLogged(httpRequest)) {
            return "redirect:/";
        }
        EmployeesRootDto projectRootDto = this.converter
                .deserialize(request.getEmployees(), EmployeesRootDto.class);

        projectRootDto.getEmployees().forEach(this.employeeService::create);

        return "redirect:/import/xml";
    }
}
