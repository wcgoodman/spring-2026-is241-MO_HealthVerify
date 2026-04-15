package com.mohealthverify.controller;

import com.mohealthverify.dto.LoginRequest;
import com.mohealthverify.dto.ProfileResponse;
import com.mohealthverify.dto.RegisterRequest;
import com.mohealthverify.dto.UploadRequest;
import com.mohealthverify.service.UserService;
import com.mohealthverify.service.UploadService;
import com.mohealthverify.dto.ProfileUpdateRequest;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            userService.register(
                    request.getFirstName(),
                    request.getLastName(),
                    request.getEmail(),
                    request.getPassword()
            );
            return ResponseEntity.ok(Map.of("success", true, "message", "Registration successful"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {

        Long userId = userService.loginAndReturnUserId(request.getEmail(), request.getPassword());

        if (userId != null) {
            session.setAttribute("userId", userId);
            session.setAttribute("userEmail", request.getEmail());

            return ResponseEntity.ok(Map.of("success", true, "message", "Login successful"));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("success", false, "message", "Invalid email or password"));
    }

    // UPLOAD — uses logged-in session userId
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestBody UploadRequest request, HttpSession session) {
        try {
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("success", false, "message", "User not logged in"));
            }

            request.setUser_id(userId);
            uploadService.handleUpload(request);

            return ResponseEntity.ok(Map.of("success", true, "message", "Upload successful"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Upload failed: " + e.getMessage()));
        }
    }

    // GET UPLOADS
    @GetMapping("/uploads")
    public ResponseEntity<?> getUploads(HttpSession session) {
        try {
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("success", false, "message", "User not logged in"));
            }

            var uploads = uploadService.getUploadsByUser(userId);
            return ResponseEntity.ok(Map.of("success", true, "uploads", uploads));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Failed to fetch uploads"));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "Not logged in"));
        }

        try {
            ProfileResponse profile = userService.getProfile(userId);
            return ResponseEntity.ok(profile);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @PostMapping("/profile/update")
    public ResponseEntity<?> updateProfile(
            @RequestBody ProfileUpdateRequest request,
            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "Not logged in"));
        }

        try {
            ProfileResponse updatedProfile = userService.updateProfile(userId, request);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Profile updated successfully",
                    "profile", updatedProfile
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}
