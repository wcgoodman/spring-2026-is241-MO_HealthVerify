package com.mohealthverify.entity;

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

    @Column(name = "first_name")   // <-- added
    private String firstName;

    @Column(name = "middle_initial")
    private String middleInitial;

    @Column(name = "last_name")    // <-- added
    private String lastName;

    @Column(name = "datetime_registered", nullable = false)
    private OffsetDateTime datetimeRegistered;

    @Column(name = "last_login")
    private OffsetDateTime lastLogin;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }   // <-- added
    public void setFirstName(String firstName) { this.firstName = firstName; }  // <-- added

    public String getMiddleInitial() { return middleInitial; }
    public void setMiddleInitial(String middleInitial) { this.middleInitial = middleInitial; }

    public String getLastName() { return lastName; }     // <-- added
    public void setLastName(String lastName) { this.lastName = lastName; }      // <-- added

    public OffsetDateTime getDatetimeRegistered() { return datetimeRegistered; }
    public void setDatetimeRegistered(OffsetDateTime datetimeRegistered) { this.datetimeRegistered = datetimeRegistered; }

    public OffsetDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(OffsetDateTime lastLogin) { this.lastLogin = lastLogin; }
}