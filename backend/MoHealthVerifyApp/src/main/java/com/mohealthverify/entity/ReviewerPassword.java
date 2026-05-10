package com.mohealthverify.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "reviewer_passwords")
public class ReviewerPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "password_id")
    private Long passwordId;

    @Column(name = "reviewer_id")
    private Long reviewerId;

    private String salt;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "password_last_updated")
    private OffsetDateTime passwordLastUpdated;

    public Long getPasswordId() {
        return passwordId;
    }

    public void setPasswordId(Long passwordId) {
        this.passwordId = passwordId;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public OffsetDateTime getPasswordLastUpdated() {
        return passwordLastUpdated;
    }

    public void setPasswordLastUpdated(OffsetDateTime passwordLastUpdated) {
        this.passwordLastUpdated = passwordLastUpdated;
    }
}