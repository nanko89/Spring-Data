package hiberspring.service.impl;

import hiberspring.domain.dtos.xml.EmployeeSeedRootDto;
import hiberspring.domain.entities.Employee;
import hiberspring.repository.EmployeeRepository;
import hiberspring.service.BranchService;
import hiberspring.service.EmployeeCardService;
import hiberspring.service.EmployeeService;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    public static final String EMPLOYEE_FILE_PATH = "src/main/resources/files/employees.xml";

    private final EmployeeRepository employeeRepository;
    private final EmployeeCardService employeeCardService;
    private final BranchService branchService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeCardService employeeCardService,
                               BranchService branchService, ModelMapper modelMapper, ValidationUtil validationUtil,
                               FileUtil fileUtil, XmlParser xmlParser) {
        this.employeeRepository = employeeRepository;
        this.employeeCardService = employeeCardService;
        this.branchService = branchService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return fileUtil.readFile(EMPLOYEE_FILE_PATH);
    }

    @Override
    public String importEmployees() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(EMPLOYEE_FILE_PATH, EmployeeSeedRootDto.class)
                .getEmployees()
                .stream()
                .filter(employeeSeedDto -> {

                    boolean isValid = validationUtil.isValid(employeeSeedDto)
                            && branchService.isExistEntity(employeeSeedDto.getBranch())
                            && employeeCardService.isExistEntity(employeeSeedDto.getCard());

                    sb.append(isValid
                                    ? String.format("Successfully imported Employee %s %s.",
                                    employeeSeedDto.getFirstName(), employeeSeedDto.getLastName())
                                    : "Error: Invalid data.")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(employeeSeedDto -> {

                    Employee employee = modelMapper.map(employeeSeedDto, Employee.class);
                    employee.setBranch(branchService.findByName(employeeSeedDto.getBranch()));
                    employee.setCard(employeeCardService.findByNumber(employeeSeedDto.getCard()));
                    return employee;
                })
                .forEach(employeeRepository::save);
        return sb.toString();
    }

    @Override
    public String exportProductiveEmployees() {
        return employeeRepository.findByBranchContainsAndBranch_ProductsOrderByFirstNameLastName()
                .stream()
                .map(e -> String.format("Name: %s\n" +
                                "Position: %s\n" +
                                "Card Number: %s\n" +
                                "-------------------------",
                        e.getName(), e.getPosition(), e.getCardNumber()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
