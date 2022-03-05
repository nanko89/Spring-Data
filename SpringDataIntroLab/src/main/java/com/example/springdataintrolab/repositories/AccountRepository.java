package com.example.springdataintrolab.repositories;

import com.example.springdataintrolab.moduls.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository <Account, Long>{
    Account findAccountById(Long id);
}
