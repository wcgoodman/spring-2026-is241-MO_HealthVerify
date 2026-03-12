package com.mohealthverify.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_security_qs_as")
public class UserSecurityQA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_security_qa_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "security_question_id")
    private Long securityQuestionId;

    @Column(name = "answer_hash")
    private String answerHash;

}