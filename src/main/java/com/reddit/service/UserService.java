package com.reddit.service;

import com.reddit.entity.User;
import com.reddit.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public User getUserByUsername(String username){
     Optional<User> userOptional=  this.userRepository.findByUsername(username);
     User user=userOptional.get();
     return user;
    }
}
