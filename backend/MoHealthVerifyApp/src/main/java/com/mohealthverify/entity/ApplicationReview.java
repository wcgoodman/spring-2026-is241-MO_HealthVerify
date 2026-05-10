package com.mohealthverify.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "application_reviews")
public class ApplicationReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_review_id")
    private Long appReviewId;

    @Column(name = "applicant_id")
    private Long applicantId;

    @Column(name = "reviewer_id")
    private Long reviewerId;

    @Column(name = "datetime_review_started")
    private OffsetDateTime datetimeReviewStarted;

    @Column(name = "datetime_review_updated")
    private OffsetDateTime datetimeReviewUpdated;

    @Column(name = "datetime_review_completed")
    private OffsetDateTime datetimeReviewCompleted;

    @Column(name = "review_feedback", length = 2000)
    private String reviewFeedback;

    @Column(name = "applicant_comments", length = 2000)
    private String applicantComments;

    @Column(name = "review_status_id")
    private Long reviewStatusId;

    public Long getAppReviewId() {
        return appReviewId;
    }

    public void setAppReviewId(Long appReviewId) {
        this.appReviewId = appReviewId;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public OffsetDateTime getDatetimeReviewStarted() {
        return datetimeReviewStarted;
    }

    public void setDatetimeReviewStarted(OffsetDateTime datetimeReviewStarted) {
        this.datetimeReviewStarted = datetimeReviewStarted;
    }

    public OffsetDateTime getDatetimeReviewUpdated() {
        return datetimeReviewUpdated;
    }

    public void setDatetimeReviewUpdated(OffsetDateTime datetimeReviewUpdated) {
        this.datetimeReviewUpdated = datetimeReviewUpdated;
    }

    public OffsetDateTime getDatetimeReviewCompleted() {
        return datetimeReviewCompleted;
    }

    public void setDatetimeReviewCompleted(OffsetDateTime datetimeReviewCompleted) {
        this.datetimeReviewCompleted = datetimeReviewCompleted;
    }

    public String getReviewFeedback() {
        return reviewFeedback;
    }

    public void setReviewFeedback(String reviewFeedback) {
        this.reviewFeedback = reviewFeedback;
    }

    public String getApplicantComments() {
        return applicantComments;
    }

    public void setApplicantComments(String applicantComments) {
        this.applicantComments = applicantComments;
    }

    public Long getReviewStatusId() {
        return reviewStatusId;
    }

    public void setReviewStatusId(Long reviewStatusId) {
        this.reviewStatusId = reviewStatusId;
    }
}