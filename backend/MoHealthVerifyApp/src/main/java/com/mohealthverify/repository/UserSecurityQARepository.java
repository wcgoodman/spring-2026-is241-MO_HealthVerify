package com.mohealthverify.repository;

import com.mohealthverify.entity.UserSecurityQA;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSecurityQARepository
        extends JpaRepository<UserSecurityQA, Long> {

    List<UserSecurityQA> findByApplicantId(Long applicantId);
}