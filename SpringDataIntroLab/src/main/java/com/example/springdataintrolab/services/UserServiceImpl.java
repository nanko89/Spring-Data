package com.example.springdataintrolab.services;

import com.example.springdataintrolab.moduls.Account;
import com.example.springdataintrolab.moduls.User;
import com.example.springdataintrolab.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        Optional<User> found = this.userRepository.findByUsername(user.getUsername());
        if (found.isEmpty()) {
            this.userRepository.save(user);
        }
    }

}
