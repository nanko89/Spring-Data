package com.example.football.service.impl;

import com.example.football.models.dto.xml.PlayerSeedRootDto;
import com.example.football.models.entity.Player;
import com.example.football.repository.PlayerRepository;
import com.example.football.service.PlayerService;
import com.example.football.service.StatService;
import com.example.football.service.TeamService;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    public static final String PLAYER_FILE_PATH = "src/main/resources/files/xml/players.xml";

    private final PlayerRepository playerRepository;
    private final TownService townService;
    private final TeamService teamService;
    private final StatService statService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParse xmlParse;

    public PlayerServiceImpl(PlayerRepository playerRepository, TownService townService,
                             TeamService teamService, StatService statService, ModelMapper modelMapper,
                             ValidationUtil validationUtil, XmlParse xmlParse) {
        this.playerRepository = playerRepository;
        this.townService = townService;
        this.teamService = teamService;
        this.statService = statService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParse = xmlParse;
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYER_FILE_PATH));
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        xmlParse.fromFile(PLAYER_FILE_PATH, PlayerSeedRootDto.class)
                .getPlayers()
                .stream()
                .filter(playerSeedDto -> {

                    boolean isValid = validationUtil.isValid(playerSeedDto)
                            && !isExistEmail(playerSeedDto.getEmail())
                            && townService.isExistEntity(playerSeedDto.getTown().getName())
                            && teamService.isExistEntity(playerSeedDto.getTeam().getName())
                            && statService.isExistId(playerSeedDto.getStat().getId());

                    sb.append(isValid
                                    ? String.format("Successfully imported Player %s %s - %s",
                                    playerSeedDto.getFirstName(), playerSeedDto.getLastName(), playerSeedDto.getPosition().toString())
                                    : "Invalid Player")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(playerSeedDto -> {
                    Player player = modelMapper.map(playerSeedDto, Player.class);
                    player.setStat(statService.findById(playerSeedDto.getStat().getId()));
                    player.setTeam(teamService.findByName(playerSeedDto.getTeam().getName()));
                    player.setTown(townService.findByName(playerSeedDto.getTown().getName()));
                    player.setBirthDate(LocalDate.parse(playerSeedDto.getBirthDate(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    return player;
                })
                .forEach(playerRepository::save);

        return sb.toString();
    }

    private boolean isExistEmail(String email) {
        return playerRepository.existsByEmail(email);
    }

    @Override
    public String exportBestPlayers() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String startDate = "01/01/1995";
        String endDate = "01/01/2003";

        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        return playerRepository.findAllByBirthDateBetweenOrderByShootingByPassingByEnduranceAndLastName(start, end)
                .stream().map(player -> String.format("Player - %s %s\n" +
                        "\tPosition - %s\n" +
                        "\tTeam - %s\n" +
                        "\tStadium - %s\n",
                                player.getFirstName(),player.getLastName(), player.getPosition().toString(),player.getTeam().getName(),
                                player.getTeam().getStadiumName())
                        ).collect(Collectors.joining());
    }
}
