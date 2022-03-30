package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.domain.dtos.json.EmployeeCardSeedDto;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.service.EmployeeCardService;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {
    public static final String EMPLOYEE_CARD_FILE_PATH = "src/main/resources/files/employee-cards.json";

    private final EmployeeCardRepository employeeCardRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final Gson gson;

    public EmployeeCardServiceImpl(EmployeeCardRepository employeeCardRepository,
                                   ModelMapper modelMapper, ValidationUtil validationUtil,
                                   Gson gson, FileUtil fileUtil) {
        this.employeeCardRepository = employeeCardRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.fileUtil = fileUtil;
    }

    @Override
    public Boolean employeeCardsAreImported() {
        return this.employeeCardRepository.count() > 0;
    }

    @Override
    public String readEmployeeCardsJsonFile() throws IOException {
        return fileUtil.readFile(EMPLOYEE_CARD_FILE_PATH);
    }

    @Override
    public String importEmployeeCards(String employeeCardsFileContent) throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readEmployeeCardsJsonFile(), EmployeeCardSeedDto[].class))
                .filter(employeeCardSeedDto -> {
                    boolean isValid = validationUtil.isValid(employeeCardSeedDto)
                            && !isExistEntity(employeeCardSeedDto.getNumber());

                    sb.append(isValid
                    ? String.format("Successfully imported Employee Card %s.",
                            employeeCardSeedDto.getNumber())
                            : "Error: Invalid data.")
                            .append(System.lineSeparator());

                    return isValid;
                } )
                .map(employeeCardSeedDto -> modelMapper.map(employeeCardSeedDto, EmployeeCard.class))
                .forEach(employeeCardRepository::save);
        return sb.toString();
    }

    @Override
    public boolean isExistEntity(String number) {
        return employeeCardRepository.existsByNumber(number);
    }

    @Override
    public EmployeeCard findByNumber(String card) {
        return employeeCardRepository.findByNumber(card);
    }
}
