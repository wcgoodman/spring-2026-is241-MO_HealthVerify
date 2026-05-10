package com.mohealthverify.controller;

import com.mohealthverify.dto.LoginRequest;
import com.mohealthverify.dto.RegisterRequest;
import com.mohealthverify.dto.UploadRequest;
import com.mohealthverify.dto.ProfileUpdateRequest;

import com.mohealthverify.entity.User;

import com.mohealthverify.service.UserService;
import com.mohealthverify.service.UploadService;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final UploadService uploadService;

    public AuthController(UserService userService, UploadService uploadService) {
        this.userService = userService;
        this.uploadService = uploadService;
    }

    // REGISTER APPLICANT
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        userService.register(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Applicant registered successfully"
        ));
    }

    // LOGIN APPLICANT
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request,
            HttpSession session
    ) {

        Long applicantId = userService.loginAndReturnUserId(
                request.getEmail(),
                request.getPassword()
        );

        if (applicantId == null) {
            return ResponseEntity.status(401).body(Map.of(
                    "success", false,
                    "message", "Invalid email or password"
            ));
        }

        session.setAttribute("applicantId", applicantId);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Login successful",
                "applicantId", applicantId
        ));
    }

    // GET CURRENT APPLICANT PROFILE
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpSession session) {

        Long applicantId = (Long) session.getAttribute("applicantId");

        if (applicantId == null) {
            return ResponseEntity.status(401).body(Map.of(
                    "success", false,
                    "message", "Applicant not logged in"
            ));
        }

        User applicant = userService.getUserById(applicantId);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "applicant", applicant
        ));
    }

    // UPDATE CURRENT APPLICANT PROFILE
    @PostMapping("/profile/update")
    public ResponseEntity<?> updateProfile(
            @RequestBody ProfileUpdateRequest request,
            HttpSession session
    ) {

        Long applicantId = (Long) session.getAttribute("applicantId");

        if (applicantId == null) {
            return ResponseEntity.status(401).body(Map.of(
                    "success", false,
                    "message", "Applicant not logged in"
            ));
        }

        User updated = userService.updateProfile(
                applicantId,
                request.getFirstName(),
                request.getLastName(),
                request.getEmail()
        );

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Profile updated successfully",
                "applicant", updated
        ));
    }

    // UPLOAD APPLICANT DOCUMENT
    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @RequestBody UploadRequest request,
            HttpSession session
    ) {

        Long applicantId = (Long) session.getAttribute("applicantId");

        if (applicantId == null) {
            return ResponseEntity.status(401).body(Map.of(
                    "success", false,
                    "message", "Applicant not logged in"
            ));
        }

        request.setApplicant_id(applicantId);
        uploadService.handleUpload(request);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Document uploaded successfully"
        ));
    }

    // RETURN APPLICANT UPLOADED DOCUMENTS
    @GetMapping("/uploads")
    public ResponseEntity<?> getUploads(HttpSession session) {

        Long applicantId = (Long) session.getAttribute("applicantId");

        if (applicantId == null) {
            return ResponseEntity.status(401).body(Map.of(
                    "success", false,
                    "message", "Applicant not logged in"
            ));
        }

        return ResponseEntity.ok(Map.of(
                "success", true,
                "uploads", uploadService.getUploadsByApplicant(applicantId)
        ));
    }

    // LOGOUT
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {

        session.invalidate();

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Logged out successfully"
        ));
    }
}