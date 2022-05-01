package com.example.springdata.service;

import com.example.springdata.dto.UserRegisterDto;

public interface UserService {

    boolean register(UserRegisterDto user);
}
