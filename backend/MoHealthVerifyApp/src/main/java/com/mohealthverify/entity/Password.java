package com.mohealthverify.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "passwords")
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passwordId;

    private Long userId;

    private String salt;

    private String passwordHash;

    private OffsetDateTime passwordLastUpdated;

    // Getters and Setters
    public Long getPasswordId() { return passwordId; }
    public void setPasswordId(Long passwordId) { this.passwordId = passwordId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getSalt() { return salt; }
    public void setSalt(String salt) { this.salt = salt; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public OffsetDateTime getPasswordLastUpdated() { return passwordLastUpdated; }
    public void setPasswordLastUpdated(OffsetDateTime passwordLastUpdated) { this.passwordLastUpdated = passwordLastUpdated; }
}