package user.system;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import user.system.servises.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final UserService userService;
    private final Scanner scanner = new Scanner(System.in);

    public CommandLineRunnerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
//        printAllUsernamesAndEmailsByEmailProvider();
//        removeInActiveUsers();
    }

    private void removeInActiveUsers() {
        System.out.println("Enter date in format dd/MM/yyyy:");
        String stringDate = scanner.nextLine();
        LocalDate localDate = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Integer removeUsers = userService.removeAllInActiveUsers(localDate);
        System.out.println(removeUsers);
    }

    private void printAllUsernamesAndEmailsByEmailProvider() {
        System.out.println("Enter provider: ");
        String provider = scanner.nextLine();
        userService.findAllUsernamesAndEmailsByEmailProvider(provider).forEach(System.out::println);
    }

}
