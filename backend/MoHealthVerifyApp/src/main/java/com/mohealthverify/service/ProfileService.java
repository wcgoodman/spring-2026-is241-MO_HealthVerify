package com.mohealthverify.service;

import com.mohealthverify.dto.ProfileUpdateRequest;
import com.mohealthverify.entity.ApplicantProfile;
import com.mohealthverify.entity.User;
import com.mohealthverify.repository.ApplicantProfileRepository;
import com.mohealthverify.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.LocalDate;

@Service
public class ProfileService {

    private final ApplicantProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(ApplicantProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public ApplicantProfile updateProfile(Long userId, ProfileUpdateRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ApplicantProfile profile = profileRepository.findByUserId(userId)
                .orElse(new ApplicantProfile());

        profile.setUser(user);
        profile.setIsMissouriResident(request.getIsMissouriResident());

        if (request.getDateOfBirth() != null) {
            profile.setDateOfBirth(LocalDate.parse(request.getDateOfBirth()));
        }

        profile.setAddress1(request.getAddress1());
        profile.setAddress2(request.getAddress2());
        profile.setAddressCity(request.getAddressCity());
        profile.setAddressState(request.getAddressState());
        profile.setAddressZipCode(request.getAddressZipCode());

        profile.setProfileLastUpdated(OffsetDateTime.now());

        return profileRepository.save(profile);
    }
}