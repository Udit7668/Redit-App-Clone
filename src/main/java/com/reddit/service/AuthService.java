package com.reddit.service;

import com.reddit.dto.RegisterRequest;
import com.reddit.entity.User;
import com.reddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Transactional
    public void signUp(RegisterRequest registerRequest){
        User user=new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnabled(false);
        userRepository.save(user);
    }

    private void generateVerificationToken(User user){
       String token= UUID.randomUUID().toString();
    }
}
