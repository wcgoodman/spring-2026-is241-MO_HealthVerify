package com.mohealthverify.service;

import com.mohealthverify.dto.UploadRequest;
import com.mohealthverify.entity.Upload;
import com.mohealthverify.repository.UploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
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

    // Get uploads by user
    public List<Upload> getUploadsByUser(Long userId) {
        return uploadRepository.findByUserId(userId);
    }

    // Handle upload
    public void handleUpload(UploadRequest request) throws Exception {

        if (request.getFile_data() == null || request.getFile_data().isEmpty()) {
            throw new Exception("File data is empty");
        }
        if (request.getUser_id() == null) {
            throw new Exception("User ID is required");
        }
        if (request.getFile_name() == null || request.getFile_name().isEmpty()) {
            throw new Exception("File name is required");
        }

        File folder = new File("uploads/");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        byte[] fileBytes = Base64.getDecoder().decode(request.getFile_data());
        String cleanName = request.getFile_name().replaceAll(" ", "_");
        String fileName = System.currentTimeMillis() + "_" + cleanName;
        String filePath = "uploads/" + fileName;

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(fileBytes);
        }

        Upload upload = new Upload();
        upload.setUserId(request.getUser_id());
        upload.setUploadDescriptiveName(request.getDescriptive_name());
        upload.setUploadFileName(fileName);
        upload.setUploadFilePath(filePath);
        upload.setUploadedAt(OffsetDateTime.now());

        uploadRepository.save(upload);

        System.out.println("Upload saved: " + fileName + " for user " + request.getUser_id());
    }
}