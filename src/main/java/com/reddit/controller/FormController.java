package com.reddit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.reddit.dto.RegisterRequest;
import com.reddit.entity.User;
import com.reddit.service.AuthService;
import com.reddit.service.UserService;

@Controller
@RequestMapping("/form")
public class FormController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public String signup(){
        return "sign-up";
    }

    @RequestMapping("/createUser")
    public String createUser(@ModelAttribute RegisterRequest registerRequest,Model model){
      User userName=  this.userService.findUserByUsername(registerRequest.getUsername());
      if(userName!=null){
        model.addAttribute("Error", "Username already exists");
            return "sign-up";
      }
      User userEmail=this.userService.findUserByEmail(registerRequest.getEmail());
      if(userEmail!=null){
        model.addAttribute("Error", "Email already in use");
        return "sign-up";
      }
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
