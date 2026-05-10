package com.mohealthverify.repository;

import com.mohealthverify.entity.ReviewerPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewerPasswordRepository extends JpaRepository<ReviewerPassword, Long> {

    Optional<ReviewerPassword> findByReviewerId(Long reviewerId);
}