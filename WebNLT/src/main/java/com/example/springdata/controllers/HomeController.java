package com.example.springdata.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController extends BaseController {

    @GetMapping("/")
    public String index(HttpServletRequest request){
        if (this.isLogged(request)){
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String home(HttpServletRequest request){
        if (!this.isLogged(request)){
            return "redirect:/";
        }
        return "home";
    }
}
