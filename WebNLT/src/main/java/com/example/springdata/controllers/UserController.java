package com.example.springdata.controllers;

import com.example.springdata.dto.UserRegisterDto;
import com.example.springdata.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/register")
    public String register() {
        return "user/register";
    }

    @PostMapping("/users/register")
    public String register(UserRegisterDto user, Model model) {
        if (this.userService.register(user)){
            return "redirect:/user/login";
        }

        model.addAttribute("error","Invalid data");
        return "user/register";
    }
}
