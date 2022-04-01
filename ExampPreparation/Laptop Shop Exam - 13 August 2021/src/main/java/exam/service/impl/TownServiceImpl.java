package exam.service.impl;

import exam.model.dto.xml.TownSeedRootDto;
import exam.model.entity.Town;
import exam.repository.TownRepository;
import exam.service.TownService;
import exam.util.ValidationUtil;
import exam.util.XmlParse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TownServiceImpl implements TownService {

    public static final String TOWN_FILE_PATH = "src/main/resources/files/xml/towns.xml";

    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParse xmlParse;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParse xmlParse) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParse = xmlParse;
    }

    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWN_FILE_PATH));
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        xmlParse.fromFile(TOWN_FILE_PATH, TownSeedRootDto.class)
                .getTowns()
                .stream()
                .filter(townSeedDto -> {
                    boolean isValid = validationUtil.isValid(townSeedDto)
                            && !isExistEntity(townSeedDto.getName());
                    sb.append(isValid
                    ? String.format("Successfully imported Town %s", townSeedDto.getName())
                            : "Invalid Town")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(townSeedDto -> modelMapper.map(townSeedDto, Town.class))
                .forEach(townRepository::save);
        return sb.toString().trim();
    }

    @Override
    public Town findByName(String name) {
        return townRepository.findByName(name);
    }

    private boolean isExistEntity(String name) {
        return townRepository.existsByName(name);
    }
}
