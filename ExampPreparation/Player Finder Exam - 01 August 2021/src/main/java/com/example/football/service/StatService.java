package com.example.football.service;

import com.example.football.models.dto.xml.StatSeedDto;
import com.example.football.models.entity.Stat;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface StatService {
    boolean areImported();

    String readStatsFileContent() throws IOException;

    String importStats() throws JAXBException, FileNotFoundException;

    boolean isExistEntity(StatSeedDto statSeedDto);

    boolean isExistId(Long id);

    Stat findById(Long id);
}
