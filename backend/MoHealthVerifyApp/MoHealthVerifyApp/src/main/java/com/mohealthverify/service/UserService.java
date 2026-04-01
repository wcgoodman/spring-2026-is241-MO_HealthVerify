package com.mohealthverify.service;

import com.mohealthverify.entity.Password;
import com.mohealthverify.entity.User;
import com.mohealthverify.repository.UserRepository;
import com.mohealthverify.repository.PasswordRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordRepository passwordRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordRepository passwordRepository) {
        this.userRepository = userRepository;
        this.passwordRepository = passwordRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

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

    public boolean login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) return false;

        Password pw = passwordRepository.findByUserId(user.getId());
        if (pw == null) return false;

        return passwordEncoder.matches(password, pw.getPasswordHash());
    }
}