package com.mohealthverify.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "datetime_registered", nullable = false)
    private OffsetDateTime datetimeRegistered;

    @Column(name = "last_login")
    private OffsetDateTime lastLogin;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public OffsetDateTime getDatetimeRegistered() { return datetimeRegistered; }
    public void setDatetimeRegistered(OffsetDateTime datetimeRegistered) { this.datetimeRegistered = datetimeRegistered; }

    public OffsetDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(OffsetDateTime lastLogin) { this.lastLogin = lastLogin; }
}