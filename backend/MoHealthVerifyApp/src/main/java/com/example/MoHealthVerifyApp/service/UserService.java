/*
    Spring 2026 IS 241.210 - Team MO HealthVerify
    Written by Sumbal Shehzadi, with some assistance from Justin Whipple
 */

package com.example.MoHealthVerifyApp.service;

import com.example.MoHealthVerifyApp.dto.RegisterRequest;
import com.example.MoHealthVerifyApp.entity.User;
import com.example.MoHealthVerifyApp.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class UserService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository repo) {
        this.repo = repo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void register(RegisterRequest req) {

        // Check if email already exists
        if (repo.existsByEmail(req.getEmail())) {
            throw new IllegalStateException("Email already exists");
        }

        User user = new User();

        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setEmail(req.getEmail());

        String hashedPassword = passwordEncoder.encode(req.getPassword());
        user.setPasswordHash(hashedPassword);
        user.setSalt("BCrypt"); // Optional placeholder

        String hashedAnswer = passwordEncoder.encode(req.getSecurityAnswer());
        user.setSecurityAnswerHash(hashedAnswer);
        user.setSecurityQuestion(req.getSecurityQuestion());

        user.setDatetimeRegistered(OffsetDateTime.now());
        user.setLastLogin(null);
        user.setPasswordLastUpdated(null);

        repo.save(user);
    }
}