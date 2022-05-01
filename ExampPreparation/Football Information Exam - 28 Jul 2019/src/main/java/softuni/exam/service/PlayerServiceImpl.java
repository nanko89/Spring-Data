package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dto.json.PlayerSeedDto;
import softuni.exam.domain.entities.Player;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class PlayerServiceImpl implements PlayerService {

    public static final String PLAYER_FILE_PATH = "src/main/resources/files/json/players.json";

    private final PlayerRepository playerRepository;
    private final PictureService pictureService;
    private final TeamService teamService;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;
    private final Gson gson;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, PictureService pictureService, TeamService teamService,
                             ModelMapper modelMapper, ValidatorUtil validatorUtil, FileUtil fileUtil, Gson gson) {
        this.playerRepository = playerRepository;
        this.pictureService = pictureService;
        this.teamService = teamService;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }


    @Override
    public String importPlayers() throws IOException {

        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readPlayersJsonFile(), PlayerSeedDto[].class))
                .filter(playerSeedDto -> {

                    boolean isValid = validatorUtil.isValid(playerSeedDto)
                            && pictureService.isExistEntity(playerSeedDto.getPicture().getUrl())
                            && pictureService.isExistEntity(playerSeedDto.getTeam().getPicture().getUrl())
                            && teamService.isExistEntity(playerSeedDto.getTeam().getName());

                    sb.append(isValid
                                    ? String.format("Successfully imported player: %s %s",
                                    playerSeedDto.getFirstName(), playerSeedDto.getLastName())
                                    : "Invalid Player")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(playerSeedDto -> {
                    Player player = modelMapper.map(playerSeedDto, Player.class);
                    player.setPicture(pictureService.findByUrl(playerSeedDto.getPicture().getUrl()));
                    player.setTeam(teamService.findByName(playerSeedDto.getTeam().getName()));

                    return player;
                })
                .forEach(playerRepository::save);
        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return fileUtil.readFile(PLAYER_FILE_PATH);
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder sb = new StringBuilder();
        playerRepository.findAllBySalaryGreaterThanOrderBySalaryDesc(BigDecimal.valueOf(100000))
                .forEach(p -> sb.append(String.format("Player name: %s %s \n" +
                        "Number: %d\n" +
                        "Salary: %.2f\n" +
                        "Team: %s\n", p.getFirstName(), p.getLastName(),
                        p.getNumber(), p.getSalary(), p.getTeam().getName())));
        return sb.toString();
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder sb = new StringBuilder();
        sb.append("Team: North Hub");
        playerRepository.findAllByTeamName("North Hub")
                .forEach(player -> sb.append(String.format
                        ("Player name: %s %s - %s\n" +
                                        "Number: %d\n", player.getFirstName(),
                                player.getLastName(), player.getPosition(), player.getNumber())));
        return sb.toString();
    }
}
