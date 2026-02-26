/*
    Spring 2026 IS 241.210 - Team MO HealthVerify
    Written by Sumbal Shehzadi, with some assistance from Justin Whipple
 */

package com.example.MoHealthVerifyApp.dto;

public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String securityQuestion;
    private String securityAnswer;

    // ---------------- Getters & Setters ----------------

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getSecurityQuestion() { return securityQuestion; }
    public void setSecurityQuestion(String securityQuestion) { this.securityQuestion = securityQuestion; }

    public String getSecurityAnswer() { return securityAnswer; }
    public void setSecurityAnswer(String securityAnswer) { this.securityAnswer = securityAnswer; }
}
