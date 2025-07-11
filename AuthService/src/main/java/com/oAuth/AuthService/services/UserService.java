package com.oAuth.AuthService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oAuth.AuthService.models.UserModel;
import com.oAuth.AuthService.repositories.UserRepository;


@Service
public class UserService  {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean emailExists(String email){
        return userRepo.findByEmail(email).isPresent();
    }

    public UserModel registerUser(String name,String password, String email){
        String hashedPassword = passwordEncoder.encode(password);
        UserModel user = new UserModel(name,email,hashedPassword);
        return userRepo.save(user);
    }


    public UserModel findUser(String email){
        return userRepo.findByEmail(email).orElse(null);
    }
    
}