package com.mohealthverify.service;

import com.mohealthverify.dto.UpdateReviewRequest;
import com.mohealthverify.entity.ApplicationReview;
import com.mohealthverify.repository.ApplicationReviewRepository;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ApplicationReviewRepository applicationReviewRepository;

    public ReviewService(ApplicationReviewRepository applicationReviewRepository) {
        this.applicationReviewRepository = applicationReviewRepository;
    }

    public List<ApplicationReview> getUnassigned() {
        return applicationReviewRepository.findByReviewerIdIsNull();
    }

    public List<ApplicationReview> getMyActive(Long reviewerId) {
        return applicationReviewRepository.findByReviewerIdAndReviewStatusIdNotIn(
                reviewerId,
                List.of(4L, 5L)
        );
    }

    public ApplicationReview assignToMe(Long appReviewId, Long reviewerId) {
        ApplicationReview review = applicationReviewRepository.findById(appReviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (review.getReviewerId() != null) {
            throw new RuntimeException("Review already assigned");
        }

        review.setReviewerId(reviewerId);

        if (review.getDatetimeReviewStarted() == null) {
            review.setDatetimeReviewStarted(OffsetDateTime.now());
        }

        review.setDatetimeReviewUpdated(OffsetDateTime.now());

        return applicationReviewRepository.save(review);
    }

    public ApplicationReview updateByReviewer(
            Long appReviewId,
            Long reviewerId,
            UpdateReviewRequest request
    ) {
        ApplicationReview review = applicationReviewRepository.findById(appReviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setReviewerId(reviewerId);

        if (review.getDatetimeReviewStarted() == null) {
            review.setDatetimeReviewStarted(OffsetDateTime.now());
        }

        if (request.getReviewStatusId() != null) {
            review.setReviewStatusId(request.getReviewStatusId());

            if (request.getReviewStatusId() == 4L || request.getReviewStatusId() == 5L) {
                review.setDatetimeReviewCompleted(OffsetDateTime.now());
            }
        }

        if (request.getReviewFeedback() != null) {
            review.setReviewFeedback(request.getReviewFeedback());
        }

        review.setDatetimeReviewUpdated(OffsetDateTime.now());

        return applicationReviewRepository.save(review);
    }

    public ApplicationReview updateByApplicant(
            Long appReviewId,
            Long applicantId,
            UpdateReviewRequest request
    ) {
        ApplicationReview review = applicationReviewRepository.findById(appReviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getApplicantId().equals(applicantId)) {
            throw new RuntimeException("You cannot update another applicant's review");
        }

        if (request.getApplicantComments() != null) {
            review.setApplicantComments(request.getApplicantComments());
        }

        review.setReviewStatusId(3L); // Feedback provided
        review.setDatetimeReviewUpdated(OffsetDateTime.now());

        return applicationReviewRepository.save(review);
    }
}