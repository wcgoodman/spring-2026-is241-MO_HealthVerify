package com.mohealthverify.controller;

import com.mohealthverify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Register endpoint
    @PostMapping("/register")
    public String register(@RequestParam String email,
                           @RequestParam String password) {
        try {
            userService.register(email, password);
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