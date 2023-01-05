package com.reddit.service;

import com.reddit.entity.CustomUserDetail;
import com.reddit.entity.User;
import com.reddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional= this.userRepository.findByUsername(username);
       User user=userOptional.get();
        if(user==null){
            throw new UsernameNotFoundException("No User found");
        }

        return new CustomUserDetail(user);
    }

}
