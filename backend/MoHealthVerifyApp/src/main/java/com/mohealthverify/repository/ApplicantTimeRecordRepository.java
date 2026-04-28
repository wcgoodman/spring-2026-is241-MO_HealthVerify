package com.mohealthverify.repository;

import com.mohealthverify.entity.ApplicantTimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicantTimeRecordRepository extends JpaRepository<ApplicantTimeRecord, Long> {

    List<ApplicantTimeRecord> findByUserIdOrderByTimeRecordStartingDatetimeDesc(Long userId);
}