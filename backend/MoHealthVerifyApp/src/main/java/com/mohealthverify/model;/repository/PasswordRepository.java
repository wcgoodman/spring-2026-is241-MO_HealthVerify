package com.mohealthverify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mohealthverify.model.Password;

public interface PasswordRepository extends JpaRepository<Password, Long> {

    Password findByUserId(Long user_id);

}