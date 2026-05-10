package com.mohealthverify.service;

import com.mohealthverify.dto.UploadRequest;
import com.mohealthverify.entity.Upload;
import com.mohealthverify.repository.UploadRepository;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class UploadService {

    private final UploadRepository uploadRepository;

    public UploadService(UploadRepository uploadRepository) {
        this.uploadRepository = uploadRepository;
    }

    public void handleUpload(UploadRequest request) {

        Upload upload = new Upload();

        upload.setApplicantId(request.getApplicant_id());
        upload.setUploadDescriptiveName(request.getDescriptive_name());
        upload.setUploadFileName(request.getFile_name());
        upload.setUploadFilePath(request.getFile_name());
        upload.setUploadedAt(OffsetDateTime.now());

        uploadRepository.save(upload);
    }

    public List<Upload> getUploadsByApplicant(Long applicantId) {
        return uploadRepository.findByApplicantId(applicantId);
    }
}