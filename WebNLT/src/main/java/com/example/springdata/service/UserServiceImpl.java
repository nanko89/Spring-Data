package com.example.springdata.service;

import com.example.springdata.dto.UserLoginDto;
import com.example.springdata.dto.UserRegisterDto;
import com.example.springdata.entity.User;
import com.example.springdata.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean register(UserRegisterDto userRequest) {
        if (this.userRepository.existsByUsernameOrEmail(
                userRequest.getUsername(),
                userRequest.getEmail()
        )) {
            return false;
        }

        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            return false;
        }

        User user = this.modelMapper.map(userRequest, User.class);

        this.userRepository.save(user);
        return true;
    }

    @Override
    public Long validateUserLoginDetails(UserLoginDto userRequest) {
        User user = this.userRepository.findFirstByUsername(userRequest.getUsername());

        if (user == null){
            return  null;
        }

        if (!user.getPassword().equals(userRequest.getPassword())){
            return null;
        }


        return user.getId();
    }


}
