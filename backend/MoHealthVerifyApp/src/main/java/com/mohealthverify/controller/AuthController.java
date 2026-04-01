package com.mohealthverify.controller;

import com.mohealthverify.dto.LoginRequest;
import com.mohealthverify.dto.RegisterRequest;
import com.mohealthverify.dto.UploadRequest;
import com.mohealthverify.service.UserService;
import com.mohealthverify.service.UploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final UploadService uploadService;

    @Autowired
    public AuthController(UserService userService, UploadService uploadService) {
        this.userService = userService;
        this.uploadService = uploadService;
    }

    // Register endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            userService.register(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword());
            return ResponseEntity.ok(Map.of("success", true, "message", "Registration successful."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        boolean success = userService.login(request.getEmail(), request.getPassword());
        if (success) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Login successful"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("success", false, "message", "Invalid email or password"));
    }

    // Upload endpoint
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestBody UploadRequest request) {
        try {
            uploadService.handleUpload(request);
            return ResponseEntity.ok("Upload successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Upload failed");
        }
    }

    @GetMapping("/uploads/{email}")
    public ResponseEntity<?> getUploads(@PathVariable String email) {
        try {
            return ResponseEntity.ok(uploadService.getUploadsByUser(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to fetch uploads");
        }
    }
}