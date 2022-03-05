package com.example.springdataintrolab;

import com.example.springdataintrolab.moduls.Account;
import com.example.springdataintrolab.moduls.User;
import com.example.springdataintrolab.services.AccountService;
import com.example.springdataintrolab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final UserService userService;
    private final AccountService accountService;

    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        User first = new User("gogo", 20 );
        userService.registerUser(first);

        Account account1 = new Account(BigDecimal.valueOf(50), first);

        accountService.withdrawMoney(BigDecimal.valueOf(40), 3L);

    }
}
