package com.mohealthverify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mohealthverify.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}