package com.example.productShop.service.impl;

import com.example.productShop.model.dto.user.*;
import com.example.productShop.model.entity.User;
import com.example.productShop.reposiitory.UserRepository;
import com.example.productShop.service.UserService;
import com.example.productShop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Long getEntityCount() {
        return userRepository.count();
    }

    @Override
    public void seedUser(List<UserSeedDTO> user) {
        user
                .stream()
                .filter(validationUtil::isValid)
                .map(userSeedDTO -> modelMapper.map(userSeedDTO, User.class))
                .forEach(userRepository::save);
    }

    @Override
    public User getRandomUser() {
        Random random = new Random();

        long size = userRepository.count();

        Long randomId = random.nextLong(1, size + 1);

        return userRepository.findById(randomId).orElse(null);
    }

    @Override
    public UserViewRootDTO findAllUserWithSoldProductOrderByLastAndFirstName() {
        UserViewRootDTO userViewRootDTO = new UserViewRootDTO();
        userViewRootDTO.setUser(userRepository
                .findAllUserWithMoreThanOneSoldProduct()
                .stream()
                .map(user -> modelMapper.map(user, UserWithProductsDTO.class))
                .collect(Collectors.toList()));
        return userViewRootDTO;
    }

    @Override
    public UserCountViewRootDTO findAllUserWithSoldProductOrderByCountLastAndFirstName() {
        UserCountViewRootDTO userCountViewRootDTO = new UserCountViewRootDTO();

        userCountViewRootDTO.setUsers(userRepository
                .findAllUserWithMoreThanOneSoldProductOrderByCount()
                .stream()
                .map(user -> modelMapper.map(user, UserCountWithProductsDTO.class))
                .collect(Collectors.toList()));
        userCountViewRootDTO.setCount(userCountViewRootDTO.getUsers().size());
        return userCountViewRootDTO;
    }


}
