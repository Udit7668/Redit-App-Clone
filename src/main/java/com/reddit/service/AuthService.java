package com.reddit.service;

import com.reddit.dto.RegisterRequest;
import com.reddit.entity.NotificationEmail;
import com.reddit.entity.User;
import com.reddit.entity.VerificationToken;
import com.reddit.repository.UserRepository;
import com.reddit.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private MailService mailService;
    @Transactional
    public void signUp(RegisterRequest registerRequest){
        User user=new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnabled(false);
        userRepository.save(user);
       String token= generateVerificationToken(user);
       mailService.sendEmail(new NotificationEmail("Please Activate Your Account"
       ,user.getEmail(),"please click on the below url to activate your account :"+ "http://localhost:8080/form/accountVerification/"+token));
    }

    private String generateVerificationToken(User user){
       String token= UUID.randomUUID().toString();
        VerificationToken verificationToken=new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;

    }

    public void verifyAccount(String token) {
           Optional<VerificationToken> verificationToken=verificationTokenRepository.findByToken(token);
           fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    public void fetchUserAndEnable(VerificationToken verificationToken){
        String username=verificationToken.getUser().getUsername();
        Optional<User> userOptional=userRepository.findByUsername(username);
        User user=userOptional.get();
        user.setEnabled(true);
        userRepository.save(user);
    }
}
