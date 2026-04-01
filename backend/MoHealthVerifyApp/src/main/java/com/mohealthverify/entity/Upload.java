package com.mohealthverify.entity;

import jakarta.persistence.*;

@Entity
public class Upload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;
    private String descriptiveName;
    private String fileName;
    private String filePath;

    public Long getId() { return id; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getDescriptiveName() { return descriptiveName; }
    public void setDescriptiveName(String descriptiveName) { this.descriptiveName = descriptiveName; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
}
