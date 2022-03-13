package auto.mapping.exercise.service.impl;

import auto.mapping.exercise.model.dto.GameAddDTO;
import auto.mapping.exercise.model.entity.Game;
import auto.mapping.exercise.repository.GameRepository;
import auto.mapping.exercise.service.GameService;
import auto.mapping.exercise.service.UserService;
import auto.mapping.exercise.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
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

        if (!userService.hasLoggedInUser()){
            System.out.println("No logged user! Login first");
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

    @Override
    public void editGame(Long gameId, BigDecimal price, Double size) {
        Game game = gameRepository.findById(gameId).orElse(null);

        if (game == null){
            System.out.println("Invalid game id");
            return;
        }

        game.setPrice(price);
        game.setSize(size);

        gameRepository.save(game);
    }
}
