package com.mohealthverify.repository;

import com.mohealthverify.entity.Password;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Password, Long> {

    Password findByApplicantId(Long applicantId);
}