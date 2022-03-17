package com.example.jsonexercise.service.impl;

import com.example.jsonexercise.model.dto.UserSeedDTO;
import com.example.jsonexercise.model.entity.User;
import com.example.jsonexercise.repository.UserRepository;
import com.example.jsonexercise.service.UserService;
import com.example.jsonexercise.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;


    public UserServiceImpl(UserRepository userRepository, ValidationUtil validationUtil, Gson gson, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUsers() throws IOException {
        if (userRepository.count() > 0){
            return;
        }

        String PATH_USER = "src/main/resources/Files/users.json";
        Arrays.stream(gson.fromJson(Files.readString(Path.of(PATH_USER)), UserSeedDTO[].class))
                .filter(validationUtil::isValid)
                .map(userSeedDTO -> modelMapper.map(userSeedDTO, User.class))
                .forEach(userRepository::save);
    }

    @Override
    public User findRandomUser() {
        long randomId = ThreadLocalRandom
                .current().nextLong(1,userRepository.count() + 1);
        return userRepository
                .findById(randomId)
                .orElse(null);
    }
}
