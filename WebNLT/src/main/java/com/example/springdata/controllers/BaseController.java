package com.example.springdata.controllers;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    protected boolean isLogged(HttpServletRequest request){
        Object userId = request.getSession().getAttribute("userId");

        return userId != null;
    }
}
