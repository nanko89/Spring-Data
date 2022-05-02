package com.example.springdata.service;

import com.example.springdata.dto.UserLoginDto;
import com.example.springdata.dto.UserRegisterDto;

public interface UserService {

    boolean register(UserRegisterDto user);
    
    Long validateUserLoginDetails(UserLoginDto user);
}
