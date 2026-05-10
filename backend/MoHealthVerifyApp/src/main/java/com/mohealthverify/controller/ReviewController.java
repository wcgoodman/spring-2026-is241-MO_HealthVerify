package com.mohealthverify.controller;

import com.mohealthverify.dto.UpdateReviewRequest;
import com.mohealthverify.entity.ApplicationReview;
import com.mohealthverify.service.ReviewService;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/unassigned")
    public ResponseEntity<?> getUnassigned(HttpSession session) {
        Long reviewerId = (Long) session.getAttribute("reviewerId");

        if (reviewerId == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("success", false, "message", "Reviewer not logged in"));
        }

        List<ApplicationReview> result = reviewService.getUnassigned();

        return ResponseEntity.ok(Map.of(
                "success", true,
                "reviews", result
        ));
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMy(HttpSession session) {
        Long reviewerId = (Long) session.getAttribute("reviewerId");

        if (reviewerId == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("success", false, "message", "Reviewer not logged in"));
        }

        List<ApplicationReview> result = reviewService.getMyActive(reviewerId);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "reviews", result
        ));
    }

    @PatchMapping("/{appReviewId}/assign")
    public ResponseEntity<?> assignToMe(
            @PathVariable Long appReviewId,
            HttpSession session
    ) {
        Long reviewerId = (Long) session.getAttribute("reviewerId");

        if (reviewerId == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("success", false, "message", "Reviewer not logged in"));
        }

        ApplicationReview updated = reviewService.assignToMe(appReviewId, reviewerId);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "review", updated
        ));
    }

    @PatchMapping("/{appReviewId}")
    public ResponseEntity<?> updateByReviewer(
            @PathVariable Long appReviewId,
            @RequestBody UpdateReviewRequest request,
            HttpSession session
    ) {
        Long reviewerId = (Long) session.getAttribute("reviewerId");

        if (reviewerId == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("success", false, "message", "Reviewer not logged in"));
        }

        ApplicationReview updated = reviewService.updateByReviewer(
                appReviewId,
                reviewerId,
                request
        );

        return ResponseEntity.ok(Map.of(
                "success", true,
                "review", updated
        ));
    }

    @PatchMapping("/{appReviewId}/applicant-response")
    public ResponseEntity<?> updateByApplicant(
            @PathVariable Long appReviewId,
            @RequestBody UpdateReviewRequest request,
            HttpSession session
    ) {
        Long applicantId = (Long) session.getAttribute("applicantId");

        if (applicantId == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("success", false, "message", "Applicant not logged in"));
        }

        ApplicationReview updated = reviewService.updateByApplicant(
                appReviewId,
                applicantId,
                request
        );

        return ResponseEntity.ok(Map.of(
                "success", true,
                "review", updated
        ));
    }
}