package com.mohealthverify.controller;

import com.mohealthverify.entity.ApplicantTimeRecord;
import com.mohealthverify.entity.TimeRecordType;
import com.mohealthverify.repository.TimeRecordTypeRepository;
import com.mohealthverify.service.TimeTrackingService;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            @RequestParam(value = "applicantId", required = false) Long applicantId,
            HttpSession session,
            @RequestBody List<ApplicantTimeRecord> records) {
        if (applicantId == null) {
            applicantId = (Long) session.getAttribute("applicantId");
        }
        if (applicantId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User not logged in");
        }

        if (records == null || records.isEmpty()) {
            return ResponseEntity.badRequest().body("At least one time record is required");
        }

        for (ApplicantTimeRecord record : records) {
            record.setapplicantId(applicantId);
        }
        List<ApplicantTimeRecord> saved = service.saveRecords(records);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Time records submitted successfully.");
        response.put("count", saved.size());
        response.put("records", saved);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(
            @RequestParam(value = "applicantId", required = false) Long applicantId,
            HttpSession session) {
        if (applicantId == null) {
            applicantId = (Long) session.getAttribute("applicantId");
        }
        if (applicantId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User not logged in");
        }

        List<ApplicantTimeRecord> records = service.getUserHistory(applicantId);

        return ResponseEntity.ok(records);
    }

    @GetMapping("/types")
    public ResponseEntity<List<TimeRecordType>> getTypes() {

        return ResponseEntity.ok(typeRepository.findAll());
    }
}
