package com.mohealthverify.controller;

import com.mohealthverify.dto.LoginRequest;
import com.mohealthverify.dto.RegisterRequest;
import com.mohealthverify.dto.UploadRequest;
import com.mohealthverify.service.UserService;
import com.mohealthverify.service.UploadService;

import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        boolean success = userService.login(request.getEmail(), request.getPassword());
        if (success) {
            // Store user info in session
            session.setAttribute("userEmail", request.getEmail());

            return ResponseEntity.ok(Map.of("success", true, "message", "Login successful"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("success", false, "message", "Invalid email or password"));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestBody UploadRequest request) {
        try {
            System.out.println(request.getUser_id());
            uploadService.handleUpload(request);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Upload successful"
            ));
        } catch (Exception e) {
            e.printStackTrace(); // <-- print full error to console
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Upload failed: " + e.getMessage()
            ));
        }
    }

    @GetMapping("/uploads/{userId}")
    public ResponseEntity<?> getUploads(@PathVariable Long userId) {
        try {
            var uploads = uploadService.getUploadsByUser(userId);
            return ResponseEntity.ok(Map.of("success", true, "uploads", uploads));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Failed to fetch uploads"));
        }
    }
}