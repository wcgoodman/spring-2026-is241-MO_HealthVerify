package com.mohealthverify.controller;

import com.mohealthverify.dto.ProfileUpdateRequest;
import com.mohealthverify.entity.ApplicantProfile;
import com.mohealthverify.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController2 {

    private final ProfileService profileService;

    public ProfileController2(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/{applicantId}")
    public ResponseEntity<?> updateProfile(
            @PathVariable("applicantId") Long applicantId,
            @RequestBody ProfileUpdateRequest request) {

        ApplicantProfile profile = profileService.updateProfile(applicantId, request);
        return ResponseEntity.ok(profile);
    }
}
