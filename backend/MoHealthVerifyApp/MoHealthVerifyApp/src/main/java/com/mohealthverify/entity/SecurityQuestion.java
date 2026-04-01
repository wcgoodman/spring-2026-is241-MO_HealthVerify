package com.mohealthverify.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lkp_security_questions")
public class SecurityQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "security_question_id")
    private Long id;

    @Column(name = "security_question_text")
    private String questionText;

}