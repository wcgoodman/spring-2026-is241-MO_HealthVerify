package com.mohealthverify.repository;

import com.mohealthverify.entity.ApplicantProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantProfileRepository extends JpaRepository<ApplicantProfile, Long> {

    ApplicantProfile findTopByUserIdOrderByIdDesc(Long userId);
}
