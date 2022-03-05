package com.example.springdataintrolab.services;

import com.example.springdataintrolab.moduls.Account;
import com.example.springdataintrolab.moduls.User;

import java.math.BigDecimal;

public interface AccountService {
    void withdrawMoney(BigDecimal money, Long id);
    void transferMoney(BigDecimal money, Long id);

}
