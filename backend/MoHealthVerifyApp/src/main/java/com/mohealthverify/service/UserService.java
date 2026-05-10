package com.mohealthverify.service;

import com.mohealthverify.entity.ApplicationReview;
import com.mohealthverify.entity.Password;
import com.mohealthverify.entity.User;
import com.mohealthverify.repository.ApplicationReviewRepository;
import com.mohealthverify.repository.PasswordRepository;
import com.mohealthverify.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    @Autowired
    private ApplicationReviewRepository applicationReviewRepository;

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

        User savedUser = userRepository.save(user);

        Password pw = new Password();
        pw.setApplicantId(savedUser.getId());
        pw.setPasswordHash(passwordEncoder.encode(rawPassword));
        pw.setSalt("BCrypt");
        pw.setPasswordLastUpdated(OffsetDateTime.now());

        passwordRepository.save(pw);

        // Create application review when applicant registers
        ApplicationReview review = new ApplicationReview();
        review.setApplicantId(savedUser.getId());
        review.setReviewStatusId(1L); // Open

        applicationReviewRepository.save(review);
    }

    // LOGIN — returns applicantId if successful
    public Long loginAndReturnUserId(String email, String rawPassword) {

        User user = userRepository.findByEmail(email);
        if (user == null) return null;

        Password pw = passwordRepository.findByApplicantId(user.getId());
        if (pw == null) return null;

        if (passwordEncoder.matches(rawPassword, pw.getPasswordHash())) {
            user.setLastLogin(OffsetDateTime.now());
            userRepository.save(user);
            return user.getId();
        }

        return null;
    }

    // GET USER/APPLICANT BY ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // UPDATE PROFILE
    public User updateProfile(Long id, String firstName, String lastName, String email) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new RuntimeException("Applicant not found");
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        return userRepository.save(user);
    }
}