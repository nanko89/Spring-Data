package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.domain.dtos.json.TownSeedDto;
import hiberspring.domain.entities.Town;
import hiberspring.repository.TownRepository;
import hiberspring.service.TownService;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class TownServiceImpl implements TownService {

    public static final String TOWN_FILE_PATH = "src/main/resources/files/towns.json";

    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final FileUtil fileUtil;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, ValidationUtil validationUtil,
                           Gson gson, FileUtil fileUtil) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.fileUtil = fileUtil;
    }

    @Override
    public Boolean townsAreImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return fileUtil.readFile(TOWN_FILE_PATH);
    }

    @Override
    public String importTowns(String townsFileContent) throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readTownsJsonFile(), TownSeedDto[].class))
                .filter(townSeedDto -> {
                    boolean isValid = validationUtil.isValid(townSeedDto);

                    sb.append(isValid
                    ? String.format("Successfully imported Town %s.", townSeedDto.getName())
                            : "Error: Invalid data.")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(townSeedDto -> modelMapper.map(townSeedDto, Town.class))
                .forEach(townRepository::save);
        return sb.toString();
    }

    @Override
    public boolean isExistEntity(String town) {
        return townRepository.existsByName(town);
    }

    @Override
    public Town findByName(String town) {
        return townRepository.findByName(town);
    }
}
