package com.mohealthverify.service;

import com.mohealthverify.entity.ApplicantTimeRecord;
import com.mohealthverify.repository.ApplicantTimeRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeTrackingService {

    private final ApplicantTimeRecordRepository repository;

    public TimeTrackingService(ApplicantTimeRecordRepository repository) {
        this.repository = repository;
    }

    public ApplicantTimeRecord saveRecord(ApplicantTimeRecord record) {
        return repository.save(record);
    }

    public List<ApplicantTimeRecord> getUserHistory(Long userId) {
        return repository.findByUserIdOrderByTimeRecordStartingDatetimeDesc(userId);
    }
}