package com.example.productStore.service;

import com.example.productStore.model.dto.UserSellerDTO;
import com.example.productStore.model.dto.UserSoldDTO;
import com.example.productStore.model.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void seedUsers() throws IOException;

    User findRandomUser();

    List<UserSoldDTO> findAllUserWithMoreThanOneSoldProduct();

    List<UserSellerDTO> countAllUsersWithMoreThenOneSoldProduct();
}
