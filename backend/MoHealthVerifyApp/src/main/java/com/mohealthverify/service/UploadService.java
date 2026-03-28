package com.mohealthverify.service;

import com.mohealthverify.dto.UploadRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;

@Service
public class UploadService {

    public void handleUpload(UploadRequest request) throws Exception {

        // Ensure uploads folder exists
        File folder = new File("uploads/");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Decode Base64
        byte[] fileBytes = Base64.getDecoder().decode(request.getFile_data());

        // Save file
        String filePath = "uploads/" + request.getFile_name();

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(fileBytes);
        }

        System.out.println("Uploaded: " + request.getDescriptive_name());
    }
}