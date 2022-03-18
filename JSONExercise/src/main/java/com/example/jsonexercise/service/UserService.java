package com.example.jsonexercise.service;

import com.example.jsonexercise.model.dto.UserSoldDTO;
import com.example.jsonexercise.model.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void seedUsers() throws IOException;

    User findRandomUser();

    List<UserSoldDTO> findAllUserWithMoreThanOneSoldProduct();
}
