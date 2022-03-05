package com.example.springdataintrolab.services;

import com.example.springdataintrolab.moduls.Account;
import com.example.springdataintrolab.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;

    }

    @Override
    public void withdrawMoney(BigDecimal money, Long id) {


            Account account = accountRepository.getById(id);

            int result = money.compareTo(account.getBalance());


            if  (account.getUser() != null && result < 1){
                account.setBalance(account.getBalance().subtract(money));
                    accountRepository.save(account);
            }else if (result > 0){
                System.out.println(account.getUser().getUsername() + " don't have enough money");
            }else if (account.getUser() == null){
                System.out.println("Account with id " + id + " don't have user");
            }
    }

    @Override
    public void transferMoney(BigDecimal money, Long id) {
        try {
            Account account = accountRepository.findAccountById(id);
            account.setBalance(account.getBalance().add(money));
            accountRepository.save(account);
        }catch (Exception e){
            System.out.println("Not valid id");
        }
    }
}
