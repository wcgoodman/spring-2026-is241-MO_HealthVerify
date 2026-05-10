package com.mohealthverify.dto;

public class UploadRequest {

    private Long applicant_id;
    private String descriptive_name;
    private String file_name;
    private String file_data;

    public Long getApplicant_id() {
        return applicant_id;
    }

    public void setApplicant_id(Long applicant_id) {
        this.applicant_id = applicant_id;
    }

    public String getDescriptive_name() {
        return descriptive_name;
    }

    public void setDescriptive_name(String descriptive_name) {
        this.descriptive_name = descriptive_name;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_data() {
        return file_data;
    }

    public void setFile_data(String file_data) {
        this.file_data = file_data;
    }
}