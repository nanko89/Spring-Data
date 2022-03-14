package auto.mapping.exercise;

import auto.mapping.exercise.model.dto.GameAddDTO;
import auto.mapping.exercise.model.dto.UserLoginDTO;
import auto.mapping.exercise.model.dto.UserRegisterDto;
import auto.mapping.exercise.service.GameService;
import auto.mapping.exercise.service.OrderService;
import auto.mapping.exercise.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Component
public class CommandRunner implements CommandLineRunner {

    private final BufferedReader bufferedReader;
    private final UserService userService;
    private final GameService gameService;
    private final OrderService orderService;


    public CommandRunner(UserService userService, GameService gameService, OrderService orderService) {
        this.userService = userService;
        this.gameService = gameService;
        this.orderService = orderService;
        this.bufferedReader =  new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {

        while (true) {
            System.out.println("Enter your commands:");
            String[] commands = bufferedReader.readLine().split("\\|");

            switch (commands[0]){
                case "RegisterUser" -> userService
                        .registerUser(new UserRegisterDto(commands[1], commands[2], commands[3], commands[4]));
                case "LoginUser" -> userService
                        .loginUser(new UserLoginDTO(commands[1],commands[2] ));
                case "Logout" -> userService
                        .logout();
                case "AddGame" -> gameService
                        .addGame(new GameAddDTO(commands[1],
                                new BigDecimal(commands[2]),
                                Double.parseDouble(commands[3]),
                                commands[4],
                                commands[5],
                                commands[6],
                                LocalDate.parse(commands[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                case "EditGame" -> gameService
                        .editGame(Long.parseLong(commands[1])
                                , new BigDecimal(commands[2].split("=")[1]),
                                Double.parseDouble(commands[3].split("=")[1]));
                case "DeleteGame" -> gameService
                        .deleteGame(Long.parseLong(commands[1]));
                case "AllGames" -> gameService
                        .viewAllGames();
                case "DetailGame" -> gameService
                        .detailsGame(commands[1]);
                case "OwnedGames" -> userService
                        .ownedGame();
                case "AddItem" -> orderService
                        .addItem(commands[1]);

            }
        }
    }
}
