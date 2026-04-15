package com.mohealthverify.service;

import com.mohealthverify.dto.ProfileResponse;
import com.mohealthverify.dto.ProfileUpdateRequest;
import com.mohealthverify.entity.ApplicantProfile;
import com.mohealthverify.entity.User;
import com.mohealthverify.entity.Password;
import com.mohealthverify.repository.ApplicantProfileRepository;
import com.mohealthverify.repository.UserRepository;
import com.mohealthverify.repository.PasswordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    @Autowired
    private ApplicantProfileRepository applicantProfileRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // REGISTER
    public void register(String firstName, String lastName, String email, String rawPassword) {

        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setDatetimeRegistered(OffsetDateTime.now());
        user.setLastLogin(null);
        userRepository.save(user);

        Password pw = new Password();
        pw.setUserId(user.getId());
        pw.setPasswordHash(passwordEncoder.encode(rawPassword));
        pw.setSalt("BCrypt");
        pw.setPasswordLastUpdated(OffsetDateTime.now());
        passwordRepository.save(pw);

    }

    // LOGIN — returns userId if successful
    public Long loginAndReturnUserId(String email, String rawPassword) {

        User user = userRepository.findByEmail(email);
        if (user == null) return null;

        Password pw = passwordRepository.findByUserId(user.getId());
        if (pw == null) return null;

        if (passwordEncoder.matches(rawPassword, pw.getPasswordHash())) {
            user.setLastLogin(OffsetDateTime.now());
            userRepository.save(user);
            return user.getId();
        }

        return null;
    }

    // GET USER BY ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public ProfileResponse getProfile(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        ApplicantProfile profile = getLatestApplicantProfile(id);
        return toProfileResponse(user, profile);
    }

    // UPDATE PROFILE
    public ProfileResponse updateProfile(Long id, ProfileUpdateRequest request) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // update fields
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        User savedUser = userRepository.save(user);

        ApplicantProfile profile = getLatestApplicantProfile(id);
        if (profile == null) {
            profile = new ApplicantProfile();
            profile.setUserId(id);
        }

        profile.setIsMissouriResident(request.getIsMissouriResident());
        profile.setDateOfBirth(parseDate(request.getDateOfBirth()));
        profile.setAddress1(normalizeText(request.getAddress1()));
        profile.setAddress2(normalizeText(request.getAddress2()));
        profile.setAddressCity(normalizeText(request.getAddressCity()));
        profile.setAddressState(normalizeText(request.getAddressState()));
        profile.setAddressZipCode(normalizeText(request.getAddressZipCode()));
        profile.setProfileLastUpdated(OffsetDateTime.now());

        ApplicantProfile savedProfile = applicantProfileRepository.save(profile);
        return toProfileResponse(savedUser, savedProfile);
    }

    private ApplicantProfile getLatestApplicantProfile(Long userId) {
        return applicantProfileRepository.findTopByUserIdOrderByIdDesc(userId);
    }

    private ProfileResponse toProfileResponse(User user, ApplicantProfile profile) {
        ProfileResponse response = new ProfileResponse();
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setDatetimeRegistered(user.getDatetimeRegistered());
        response.setLastLogin(user.getLastLogin());

        if (profile != null) {
            response.setIsMissouriResident(profile.getIsMissouriResident());
            response.setDateOfBirth(profile.getDateOfBirth());
            response.setAddress1(profile.getAddress1());
            response.setAddress2(profile.getAddress2());
            response.setAddressCity(profile.getAddressCity());
            response.setAddressState(profile.getAddressState());
            response.setAddressZipCode(profile.getAddressZipCode());
        }

        return response;
    }

    private LocalDate parseDate(String value) {
        String normalized = normalizeText(value);
        if (normalized == null) {
            return null;
        }
        try {
            return LocalDate.parse(normalized);
        } catch (DateTimeParseException ex) {
            throw new RuntimeException("Date of birth must be in YYYY-MM-DD format");
        }
    }

    private String normalizeText(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
