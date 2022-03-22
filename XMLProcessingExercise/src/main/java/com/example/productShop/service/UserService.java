package com.example.productShop.service;

import com.example.productShop.model.dto.user.UserCountViewRootDTO;
import com.example.productShop.model.dto.user.UserSeedDTO;
import com.example.productShop.model.dto.user.UserViewRootDTO;
import com.example.productShop.model.entity.User;

import java.util.List;

public interface UserService {
    Long getEntityCount();

    void seedUser(List<UserSeedDTO> user);

    User getRandomUser();

    UserViewRootDTO findAllUserWithSoldProductOrderByLastAndFirstName();

    UserCountViewRootDTO findAllUserWithSoldProductOrderByCountLastAndFirstName();
}
