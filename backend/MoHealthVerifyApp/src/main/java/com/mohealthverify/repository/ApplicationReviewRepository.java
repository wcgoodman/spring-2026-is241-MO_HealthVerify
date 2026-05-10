package com.mohealthverify.repository;

import com.mohealthverify.entity.ApplicationReview;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationReviewRepository
        extends JpaRepository<ApplicationReview, Long> {

    List<ApplicationReview> findByReviewerIdIsNull();

    List<ApplicationReview> findByReviewerIdAndReviewStatusIdNotIn(
            Long reviewerId,
            List<Long> excludedStatuses
    );
}