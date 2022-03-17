package com.example.jsonexercise.service;

import com.example.jsonexercise.model.entity.User;

import java.io.IOException;

public interface UserService {
    void seedUsers() throws IOException;

    User findRandomUser();
}
