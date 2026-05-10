package com.mohealthverify.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lkp_review_status_codes")
public class ReviewStatusCode {

    @Id
    @Column(name = "review_status_id")
    private Long reviewStatusId;

    @Column(name = "review_status_description")
    private String reviewStatusDescription;

    public Long getReviewStatusId() {
        return reviewStatusId;
    }

    public void setReviewStatusId(Long reviewStatusId) {
        this.reviewStatusId = reviewStatusId;
    }

    public String getReviewStatusDescription() {
        return reviewStatusDescription;
    }

    public void setReviewStatusDescription(String reviewStatusDescription) {
        this.reviewStatusDescription = reviewStatusDescription;
    }
}