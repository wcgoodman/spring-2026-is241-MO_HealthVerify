/*
    Spring 2026 IS 241.210 - Team MO HealthVerify
    Written by Sumbal Shehzadi, with some assistance from Justin Whipple
 */

package com.example.MoHealthVerifyApp.repository;

import com.example.MoHealthVerifyApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
}