package com.example.football.service.impl;

import com.example.football.models.dto.xml.StatSeedDto;
import com.example.football.models.dto.xml.StatSeedRootDto;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class StatServiceImpl implements StatService {

    private static final String STAT_FILE_PATH = "src/main/resources/files/xml/stats.xml";

    private final StatRepository statRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParse xmlParse;

    @Autowired
    public StatServiceImpl(StatRepository statRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParse xmlParse) {
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParse = xmlParse;
    }

    @Override
    public boolean areImported() {
        return statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(STAT_FILE_PATH));
    }

    @Override
    public String importStats() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        xmlParse.fromFile(STAT_FILE_PATH, StatSeedRootDto.class)
                .getStats()
                .stream()
                .filter(statSeedDto -> {
                    boolean isValid = validationUtil.isValid(statSeedDto)
                            && !isExistEntity(statSeedDto);

                    sb.append(isValid
                    ? String.format("Successfully imported Stat %.2f - %.2f - %.2f",
                            statSeedDto.getShooting(), statSeedDto.getPassing(), statSeedDto.getEndurance())
                            : "Invalid Stat")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(statSeedDto -> modelMapper.map(statSeedDto, Stat.class))
                .forEach(statRepository::save);
        return sb.toString().trim();
    }

    @Override
    public Stat findById(Long id) {
      return statRepository.findById(id).orElse(null);
    }

    private boolean isExistEntity(StatSeedDto statSeedDto) {
        return statRepository.existsByEnduranceAndPassingAndShooting(statSeedDto.getEndurance(),
                statSeedDto.getPassing(),statSeedDto.getShooting());
    }
}
