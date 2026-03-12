package com.mohealthverify.controller;

import com.mohealthverify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Register endpoint
    @PostMapping("/register")
    public String register(@RequestBody com.mohealthverify.dto.RegisterRequest request) {
        try {
            userService.register(
                    request.getFirstName(),
                    request.getLastName(),
                    request.getEmail(),
                    request.getPassword()
            );
            return "Registration successful";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    // Login endpoint
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password) {
        boolean success = userService.login(email, password);
        return success ? "Login successful" : "Invalid credentials";
    }

}