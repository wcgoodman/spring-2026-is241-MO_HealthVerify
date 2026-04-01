package com.mohealthverify.service;

import com.mohealthverify.dto.UploadRequest;
import com.mohealthverify.entity.Upload;
import com.mohealthverify.repository.UploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.List;

@Service
public class UploadService {

    private final UploadRepository uploadRepository;

    @Autowired
    public UploadService(UploadRepository uploadRepository) {
        this.uploadRepository = uploadRepository;
    }

    public List<Upload> getUploadsByUser(String userEmail) {
        return uploadRepository.findByUserEmail(userEmail);
    }

    public void handleUpload(UploadRequest request) throws Exception {

        // Create uploads folder if it doesn't exist
        File folder = new File("uploads/");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Decode Base64 file
        byte[] fileBytes = Base64.getDecoder().decode(request.getFile_data());

        // Create file path
        String filePath = "uploads/" + request.getFile_name();

        // Save file locally
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(fileBytes);
        }

        // Save metadata to PostgreSQL
        Upload upload = new Upload();
        upload.setUserEmail(request.getUser_email());
        upload.setDescriptiveName(request.getDescriptive_name());
        upload.setFileName(request.getFile_name());
        upload.setFilePath(filePath);

        uploadRepository.save(upload);

        // Optional log
        System.out.println("Upload saved for user: " + request.getUser_email());
    }
}