package com.mohealthverify.dto;

public class UpdateReviewRequest {

    private Long reviewStatusId;

    private String reviewFeedback;

    private String applicantComments;

    public Long getReviewStatusId() {
        return reviewStatusId;
    }

    public void setReviewStatusId(Long reviewStatusId) {
        this.reviewStatusId = reviewStatusId;
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
}