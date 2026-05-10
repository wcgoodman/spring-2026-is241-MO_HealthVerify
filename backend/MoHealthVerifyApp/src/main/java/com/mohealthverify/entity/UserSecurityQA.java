package com.mohealthverify.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "applicant_security_qs_as")
public class UserSecurityQA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "security_qa_id")
    private Long securityQaId;

    @Column(name = "applicant_id")
    private Long applicantId;

    @Column(name = "security_question")
    private String securityQuestion;

    @Column(name = "security_answer_hash")
    private String securityAnswerHash;

    public Long getSecurityQaId() {
        return securityQaId;
    }

    public void setSecurityQaId(Long securityQaId) {
        this.securityQaId = securityQaId;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswerHash() {
        return securityAnswerHash;
    }

    public void setSecurityAnswerHash(String securityAnswerHash) {
        this.securityAnswerHash = securityAnswerHash;
    }
}