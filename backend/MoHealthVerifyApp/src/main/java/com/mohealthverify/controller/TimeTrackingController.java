package com.mohealthverify.controller;

import com.mohealthverify.entity.ApplicantTimeRecord;
import com.mohealthverify.entity.TimeRecordType;
import com.mohealthverify.repository.TimeRecordTypeRepository;
import com.mohealthverify.service.TimeTrackingService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/timetracking")
public class TimeTrackingController {

    private final TimeTrackingService service;
    private final TimeRecordTypeRepository typeRepository;

    public TimeTrackingController(TimeTrackingService service,
                                  TimeRecordTypeRepository typeRepository) {
        this.service = service;
        this.typeRepository = typeRepository;
    }

    @PostMapping
    public ResponseEntity<?> addTimeRecord(
            @RequestParam Long userId,
            @RequestBody ApplicantTimeRecord record) {

        record.setUserId(userId);
        ApplicantTimeRecord saved = service.saveRecord(record);

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(@RequestParam Long userId) {

        List<ApplicantTimeRecord> records = service.getUserHistory(userId);

        return ResponseEntity.ok(records);
    }

    @GetMapping("/types")
    public ResponseEntity<List<TimeRecordType>> getTypes() {

        return ResponseEntity.ok(typeRepository.findAll());
    }
}