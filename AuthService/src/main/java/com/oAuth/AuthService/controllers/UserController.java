package com.oAuth.AuthService.controllers;

import com.oAuth.AuthService.config.JwtConfig;
import com.oAuth.AuthService.models.UserModel;
import com.oAuth.AuthService.services.UserService;

import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

import com.oAuth.AuthService.services.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final UserService userService;

    @Autowired
    public UserController(
            AuthenticationManager authenticationManager,
            JwtConfig jwtConfig,
            UserService userService) {

        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerUser(@RequestParam String name, @RequestParam String password,
            @RequestParam String email) {

        if (userService.emailExists(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already registered");
        }

        userService.registerUser(name, password, email);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            UserModel user = userService.findUser(email);
            String jwt = jwtConfig.generateToken(user.getEmail(), user.getName(), "student");

            Cookie cookie = new Cookie("token", jwt);
            cookie.setHttpOnly(true);
            cookie.setSecure(false); // true in prod
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 10); // 10 hours

            response.addCookie(cookie);

            return ResponseEntity.ok("Login successful");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email or password");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUserInfo(@CookieValue(name = "token", required = false) String token) {
        try {
            if (token != null && !jwtConfig.isTokenExpired(token)) {
                String email = jwtConfig.extractUsername(token);
                String name = (String) jwtConfig.extractAllClaims(token).get("name");
                String role = (String) jwtConfig.extractAllClaims(token).get("role");

                return ResponseEntity.ok(Map.of(
                        "email", email,
                        "name", name,
                        "role", role));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No valid token found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error retrieving user info");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // Create an expired cookie
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Delete immediately

        // Add cookie to response
        response.addCookie(cookie);

        // Clear JSESSIONID cookie
        Cookie sessionCookie = new Cookie("JSESSIONID", null);
        sessionCookie.setHttpOnly(true);
        sessionCookie.setPath("/");
        sessionCookie.setMaxAge(0);
        response.addCookie(sessionCookie);

        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        boolean exists = userService.emailExists(email);
        if (exists) {
            UserModel user = userService.findUser(email);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not registered");
    }

}
