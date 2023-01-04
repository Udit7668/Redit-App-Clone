package com.reddit.controller;

import com.reddit.dto.RegisterRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/auth")
public class AuthController {

    @PostMapping("/signup")
    public void singUp(@RequestBody RegisterRequest registerRequest){

    }
}
