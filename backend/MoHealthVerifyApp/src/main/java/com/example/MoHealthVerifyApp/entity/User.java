package com.example.MoHealthVerifyApp.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "salt", nullable = false)
    private String salt;

    @Column(name = "security_question", nullable = false)
    private String securityQuestion;

    @Column(name = "security_answer_hash", nullable = false)
    private String securityAnswerHash;

    @Column(name = "datetime_registered", nullable = false)
    private OffsetDateTime datetimeRegistered;

    @Column(name = "last_login")
    private OffsetDateTime lastLogin;

    @Column(name = "password_last_updated")
    private OffsetDateTime passwordLastUpdated;

    // Getters & Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getSalt() { return salt; }
    public void setSalt(String salt) { this.salt = salt; }

    public String getSecurityQuestion() { return securityQuestion; }
    public void setSecurityQuestion(String securityQuestion) { this.securityQuestion = securityQuestion; }

    public String getSecurityAnswerHash() { return securityAnswerHash; }
    public void setSecurityAnswerHash(String securityAnswerHash) { this.securityAnswerHash = securityAnswerHash; }

    public OffsetDateTime getDatetimeRegistered() { return datetimeRegistered; }
    public void setDatetimeRegistered(OffsetDateTime datetimeRegistered) { this.datetimeRegistered = datetimeRegistered; }

    public OffsetDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(OffsetDateTime lastLogin) { this.lastLogin = lastLogin; }

    public OffsetDateTime getPasswordLastUpdated() { return passwordLastUpdated; }
    public void setPasswordLastUpdated(OffsetDateTime passwordLastUpdated) { this.passwordLastUpdated = passwordLastUpdated; }
}
