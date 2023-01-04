package com.reddit.controller;

import com.reddit.dto.RegisterRequest;
import com.reddit.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/form")
public class FormController {

    @Autowired
    private AuthService authService;
    @RequestMapping("/register")
    public String signup(){
        return "sign-up";
    }

    @RequestMapping("/createUser")
    public String createUser(@ModelAttribute RegisterRequest registerRequest){
        authService.signUp(registerRequest);
        return "login";
    }
}
