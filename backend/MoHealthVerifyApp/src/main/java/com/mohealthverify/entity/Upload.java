package com.mohealthverify.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "applicant_uploads")
public class Upload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upload_id")
    private Long uploadId;

    @Column(name = "applicant_id")
    private Long applicantId;

    @Column(name = "upload_descriptive_name")
    private String uploadDescriptiveName;

    @Column(name = "upload_file_name")
    private String uploadFileName;

    @Column(name = "upload_file_path")
    private String uploadFilePath;

    @Column(name = "uploaded_at")
    private OffsetDateTime uploadedAt;

    public Long getUploadId() {
        return uploadId;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public String getUploadDescriptiveName() {
        return uploadDescriptiveName;
    }

    public void setUploadDescriptiveName(String uploadDescriptiveName) {
        this.uploadDescriptiveName = uploadDescriptiveName;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getUploadFilePath() {
        return uploadFilePath;
    }

    public void setUploadFilePath(String uploadFilePath) {
        this.uploadFilePath = uploadFilePath;
    }

    public OffsetDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(OffsetDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}