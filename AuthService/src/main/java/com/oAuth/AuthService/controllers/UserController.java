package com.oAuth.AuthService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;


import com.oAuth.AuthService.services.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerUser( @RequestParam String name,@RequestParam String email,@RequestParam String password){

        if (userService.emailExists(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already registered");
        }
         
        userService.registerUser(name, email, password);
        return ResponseEntity.ok("User registered successfully");
    }



}
