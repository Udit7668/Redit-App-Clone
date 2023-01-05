package com.reddit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reddit.dto.RegisterRequest;
import com.reddit.service.AuthService;

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
        System.out.println(registerRequest);
        authService.signUp(registerRequest);
        return "login";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("accountVerification/{token}")
    public String verifyAccount(@PathVariable String token){
        authService.verifyAccount(token);
        return "login";
    }
}
