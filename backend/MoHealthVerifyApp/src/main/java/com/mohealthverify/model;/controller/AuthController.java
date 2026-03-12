package com.mohealthverify.controller;

import com.mohealthverify.dto.LoginRequest;
import com.mohealthverify.dto.RegisterRequest;
import com.mohealthverify.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterRequest request) {
        if (request == null || isBlank(request.getEmail()) || isBlank(request.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Email and password are required."
            ));
        }

        try {
            userService.register(request.getFirstName(), request.getLastName(), request.getEmail().trim(), request.getPassword());
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Registration successful."
            ));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "success", false,
                    "message", ex.getMessage()
            ));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        if (request == null || isBlank(request.getEmail()) || isBlank(request.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Email and password are required."
            ));
        }

        boolean success = userService.login(request.getEmail().trim(), request.getPassword());
        if (!success) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "success", false,
                    "message", "Invalid credentials."
            ));
        }

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Login successful."
        ));
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}