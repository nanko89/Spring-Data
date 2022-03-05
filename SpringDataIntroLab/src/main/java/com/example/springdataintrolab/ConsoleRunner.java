package com.example.springdataintrolab;

import com.example.springdataintrolab.moduls.Account;
import com.example.springdataintrolab.moduls.User;
import com.example.springdataintrolab.services.AccountService;
import com.example.springdataintrolab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;

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
        User user = new User("demo", 20 );

        Account account = new Account(BigDecimal.valueOf(5000));

        account.setUser(user);
        user.setAccount(new HashSet<>(){{add(account);}});
        userService.registerUser(user);
        accountService.withdrawMoney(BigDecimal.valueOf(1000), account.getId());
        accountService.transferMoney(BigDecimal.valueOf(2000), account.getId());

    }
}
