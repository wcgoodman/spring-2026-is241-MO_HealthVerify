package com.mohealthverify.service;

import com.mohealthverify.dto.ReviewerLoginRequest;
import com.mohealthverify.entity.Reviewer;
import com.mohealthverify.entity.ReviewerPassword;
import com.mohealthverify.repository.ReviewerPasswordRepository;
import com.mohealthverify.repository.ReviewerRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class ReviewerAuthService {

    private final ReviewerRepository reviewerRepository;
    private final ReviewerPasswordRepository reviewerPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    public ReviewerAuthService(
            ReviewerRepository reviewerRepository,
            ReviewerPasswordRepository reviewerPasswordRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.reviewerRepository = reviewerRepository;
        this.reviewerPasswordRepository = reviewerPasswordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Reviewer login(ReviewerLoginRequest request) {

        Reviewer reviewer = reviewerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Reviewer not found"));

        ReviewerPassword password = reviewerPasswordRepository
                .findByReviewerId(reviewer.getReviewerId())
                .orElseThrow(() -> new RuntimeException("Password not found"));

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                password.getPasswordHash()
        );

        if (!matches) {
            throw new RuntimeException("Invalid password");
        }

        reviewer.setLastLogin(OffsetDateTime.now());
        reviewerRepository.save(reviewer);

        return reviewer;
    }
}
