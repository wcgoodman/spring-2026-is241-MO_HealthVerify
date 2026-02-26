package com.example.MoHealthVerifyApp.controller;

import com.example.MoHealthVerifyApp.dto.RegisterRequest;
import com.example.MoHealthVerifyApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        service.register(request);
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Account registration successful!"
                )
        );
    }
}