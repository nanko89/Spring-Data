package com.example.springdata.controllers;

import com.example.springdata.dto.UserLoginDto;
import com.example.springdata.dto.UserRegisterDto;
import com.example.springdata.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

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
            return "redirect:/users/login";
        }

        model.addAttribute("error","Invalid data");
        return "user/register";
    }

    @GetMapping("/users/login")
    public String login(UserLoginDto user, Model model) {
        model.addAttribute("userLoginForm", user);
        return "user/login";
    }

    @PostMapping("/users/login")
    public String login(UserLoginDto userRequest, Model model, HttpServletRequest request){
        Long userId = this.userService.validateUserLoginDetails(userRequest);

        if (userId == null){
            model.addAttribute("error", "Invalid user");
            return "user/login";
        }

        request.getSession().setAttribute("userId", userId);

        return "redirect:/";
    }
}
