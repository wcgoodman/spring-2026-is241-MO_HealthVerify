package com.example.MoHealthVerifyApp.controller;

import com.example.MoHealthVerifyApp.dto.RegisterRequest;
import com.example.MoHealthVerifyApp.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
        try {
            service.register(request);
            return ResponseEntity.ok(
                    Map.of(
                            "success", true,
                            "message", "Account registration successful!"
                    )
            );
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(
                            Map.of(
                                    "success", false,
                                    "message", ex.getMessage()
                            )
                    );
        }
    }
}