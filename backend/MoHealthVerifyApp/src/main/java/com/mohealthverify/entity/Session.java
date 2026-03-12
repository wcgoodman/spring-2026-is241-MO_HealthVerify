package com.mohealthverify.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long sessionId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "login_time")
    private OffsetDateTime loginTime;

    @Column(name = "logout_time")
    private OffsetDateTime logoutTime;
}