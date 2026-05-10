package com.mohealthverify.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "applicant_time_records")
public class ApplicantTimeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicant_time_record_id")
    private Long applicantTimeRecordId;

    @Column(name = "applicant_id", nullable = false)
    private Long applicantId;

    @Column(name = "time_record_type_id", nullable = false)
    private Long timeRecordTypeId;

    @Column(name = "time_record_starting_datetime", nullable = false)
    private LocalDateTime timeRecordStartingDatetime;

    @Column(name = "time_record_ending_datetime")
    private LocalDateTime timeRecordEndingDatetime;

    // Getters and Setters
    public Long getApplicantTimeRecordId() { return applicantTimeRecordId; }
    public void setApplicantTimeRecordId(Long id) { this.applicantTimeRecordId = id; }

    public Long getapplicantId() { return applicantId; }
    public void setapplicantId(Long applicantId) { this.applicantId = applicantId; }

    public Long getTimeRecordTypeId() { return timeRecordTypeId; }
    public void setTimeRecordTypeId(Long timeRecordTypeId) { this.timeRecordTypeId = timeRecordTypeId; }

    public LocalDateTime getTimeRecordStartingDatetime() { return timeRecordStartingDatetime; }
    public void setTimeRecordStartingDatetime(LocalDateTime dt) { this.timeRecordStartingDatetime = dt; }

    public LocalDateTime getTimeRecordEndingDatetime() { return timeRecordEndingDatetime; }
    public void setTimeRecordEndingDatetime(LocalDateTime dt) { this.timeRecordEndingDatetime = dt; }
}