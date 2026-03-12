package com.mohealthverify.service;

import com.mohealthverify.model.User;
import com.mohealthverify.model.Password;
import com.mohealthverify.repository.UserRepository;
import com.mohealthverify.repository.PasswordRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordRepository passwordRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordRepository passwordRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordRepository = passwordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register a new user
    @Transactional
    public void register(String firstName, String lastName, String email, String password) {

        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already exists");
        }

        // Create User
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setDatetimeRegistered(OffsetDateTime.now());
        user.setLastLogin(null);

        userRepository.save(user);

        // Create Password entry
        Password pw = new Password();
        pw.setUserId(user.getId());
        pw.setPasswordHash(passwordEncoder.encode(password));
        pw.setSalt("BCrypt"); // optional placeholder
        pw.setPasswordLastUpdated(OffsetDateTime.now());
        passwordRepository.save(pw);
    }

    // Login
    public boolean login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) return false;

        Password pw = passwordRepository.findByUserId(user.getId());
        if (pw == null) return false;

        boolean matches = passwordEncoder.matches(password, pw.getPasswordHash());
        if (matches) {
            user.setLastLogin(OffsetDateTime.now());
            userRepository.save(user);
        }
        return matches;
    }

}