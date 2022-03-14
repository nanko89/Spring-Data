package auto.mapping.exercise.service.impl;

import auto.mapping.exercise.model.dto.GameAddDTO;
import auto.mapping.exercise.model.dto.GameAllDTO;
import auto.mapping.exercise.model.dto.GameDetailsDTO;
import auto.mapping.exercise.model.dto.GameOwnedDTO;
import auto.mapping.exercise.model.entity.Game;
import auto.mapping.exercise.repository.GameRepository;
import auto.mapping.exercise.service.GameService;
import auto.mapping.exercise.service.UserService;
import auto.mapping.exercise.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;



    public GameServiceImpl(GameRepository gameRepository, UserService userService, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public void addGame(GameAddDTO gameAddDTO) {

        if (isHaveValidAdminUser()) {
            return;
        }
        Set<ConstraintViolation<GameAddDTO>> violations =
                validationUtil.violation(gameAddDTO);

        if (!violations.isEmpty()){
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        Game game = modelMapper.map(gameAddDTO, Game.class);

        gameRepository.save(game);

        System.out.println("Added game "+ gameAddDTO.getTitle());

    }

    private boolean isHaveValidAdminUser() {
        if (!userService.hasLoggedInUser()){
            System.out.println("No logged user! Login first");
            return true;
        }

        if (!userService.isAdministrator()){
            System.out.println("Only administrators can add, edit or delete games");
            return true;
        }
        return false;
    }

    @Override
    public void editGame(Long gameId, BigDecimal price, Double size) {

        if (isHaveValidAdminUser()) {
            return;
        }

        Game game = gameRepository.findById(gameId).orElse(null);

        if (game == null){
            System.out.println("Invalid game id");
            return;
        }

        game.setPrice(price);
        game.setSize(size);

        gameRepository.save(game);
    }

    @Override
    public void deleteGame(long gameId) {

        if (isHaveValidAdminUser()) {
            return;
        }

        Game game = gameRepository.findById(gameId).orElse(null);

        if (game == null){
            System.out.println("Invalid game id");
            return;
        }

        gameRepository.delete(game);
    }

    @Override
    public void viewAllGames() {
        List<Game> games = gameRepository.findAll();
        for (Game game : games) {
        GameAllDTO gameAllDTO = modelMapper.map(game, GameAllDTO.class);
            System.out.println(gameAllDTO);
        }

    }

    @Override
    public void detailsGame(String gameTitle) {
        Game game = gameRepository.findByTitle(gameTitle);

        if (game == null){
            System.out.println("Invalid game title");
        }else {
            GameDetailsDTO gameDetailsDTO = modelMapper.map(game, GameDetailsDTO.class);
            System.out.println(gameDetailsDTO);
        }
    }


}
