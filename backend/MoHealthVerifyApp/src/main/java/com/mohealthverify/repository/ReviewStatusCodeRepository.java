package com.mohealthverify.repository;

import com.mohealthverify.entity.ReviewStatusCode;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewStatusCodeRepository
        extends JpaRepository<ReviewStatusCode, Long> {
}