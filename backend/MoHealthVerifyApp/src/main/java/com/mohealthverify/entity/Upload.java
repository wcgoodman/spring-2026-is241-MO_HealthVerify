package com.mohealthverify.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "uploads")
public class Upload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uploadId;

    private Long userId;

    private String uploadDescriptiveName;
    private String uploadFileName;
    private String uploadFilePath;

    private OffsetDateTime uploadedAt;

    // Getters & Setters

    public Long getUploadId() { return uploadId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUploadDescriptiveName() { return uploadDescriptiveName; }
    public void setUploadDescriptiveName(String uploadDescriptiveName) { this.uploadDescriptiveName = uploadDescriptiveName; }

    public String getUploadFileName() { return uploadFileName; }
    public void setUploadFileName(String uploadFileName) { this.uploadFileName = uploadFileName; }

    public String getUploadFilePath() { return uploadFilePath; }
    public void setUploadFilePath(String uploadFilePath) { this.uploadFilePath = uploadFilePath; }

    public OffsetDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(OffsetDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
}
