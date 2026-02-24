package com.example.MoHealthVerifyApp.service;

import com.example.MoHealthVerifyApp.dto.RegisterRequest;
import com.example.MoHealthVerifyApp.entity.User;
import com.example.MoHealthVerifyApp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public void register(RegisterRequest req) {

        // Check if email already exists
        if (repo.existsByEmail(req.email)) {
            throw new RuntimeException("Email already exists");
        }

        // Map DTO to entity
        User user = new User();
        user.setFirstName(req.firstName);
        user.setLastName(req.lastName);
        user.setEmail(req.email);
        user.setPassword(req.password);
        user.setSecurityQuestion(req.securityQuestion);
        user.setSecurityAnswer(req.securityAnswer);

        // Save user to database
        repo.save(user);
    }
}
