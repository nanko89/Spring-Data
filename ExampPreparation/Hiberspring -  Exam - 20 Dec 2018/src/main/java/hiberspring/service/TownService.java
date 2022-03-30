package hiberspring.service;

import hiberspring.domain.entities.Town;

import java.io.IOException;

public interface TownService {

    Boolean townsAreImported();

    String readTownsJsonFile() throws IOException;

    String importTowns(String townsFileContent) throws IOException;

    boolean isExistEntity(String town);

    Town findByName(String town);
}
