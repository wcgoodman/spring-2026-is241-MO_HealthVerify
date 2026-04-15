package com.mohealthverify.service;

import com.mohealthverify.entity.User;
import com.mohealthverify.entity.Password;
import com.mohealthverify.repository.UserRepository;
import com.mohealthverify.repository.PasswordRepository;

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
            return user.getId();
        }

        return null;
    }

    // GET USER BY ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // UPDATE PROFILE
    public User updateProfile(Long id, String firstName, String lastName, String email) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        String firstName = normalizeText(request.getFirstName());
        String lastName = normalizeText(request.getLastName());
        String email = normalizeText(request.getEmail());

        if (firstName == null || lastName == null || email == null) {
            throw new RuntimeException("First name, last name, and email are required");
        }

        User existingByEmail = userRepository.findByEmail(email);
        if (existingByEmail != null && !existingByEmail.getId().equals(id)) {
            throw new RuntimeException("Email already in use");
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        return userRepository.save(user);
    }
}