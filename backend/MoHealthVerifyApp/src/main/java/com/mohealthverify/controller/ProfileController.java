package com.mohealthverify.controller;

import com.mohealthverify.dto.ProfileUpdateRequest;
import com.mohealthverify.entity.ApplicantProfile;
import com.mohealthverify.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> updateProfile(
            @PathVariable Long userId,
            @RequestBody ProfileUpdateRequest request) {

        ApplicantProfile profile = profileService.updateProfile(userId, request);
        return ResponseEntity.ok(profile);
    }
}