package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dto.xml.TeamSeedRootDto;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParse;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class TeamServiceImpl implements TeamService {

    public static final String TEAM_FILE_PATH = "src/main/resources/files/xml/teams.xml";

    private final TeamRepository teamRepository;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;
    private final XmlParse xmlParse;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, PictureService pictureService, ModelMapper modelMapper, ValidatorUtil validatorUtil,
                           FileUtil fileUtil, XmlParse xmlParse) {
        this.teamRepository = teamRepository;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
        this.xmlParse = xmlParse;
    }

    @Override
    public String importTeams() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        xmlParse.fromFile(TEAM_FILE_PATH, TeamSeedRootDto.class)
                .getTeams()
                .stream()
                .filter(teamSeedDto -> {
                    boolean isValid = validatorUtil.isValid(teamSeedDto)
                            && pictureService.isExistEntity(teamSeedDto.getPicture().getUrl());

                    sb.append(isValid
                    ? String.format("Successfully imported - %s", teamSeedDto.getName())
                            : "Invalid Team")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(teamSeedDto -> {
                    Team team = modelMapper.map(teamSeedDto, Team.class);
                    team.setPicture(pictureService.findByUrl(teamSeedDto.getPicture().getUrl()));
                    return team;
                })
                .forEach(teamRepository::save);
        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return fileUtil.readFile(TEAM_FILE_PATH);
    }

    @Override
    public boolean isExistEntity(String name) {
        return teamRepository.existsByName(name);
    }

    @Override
    public Team findByName(String name) {
       return teamRepository.findByName(name);
    }
}
