package com.mohealthverify.repository;

import com.mohealthverify.entity.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UploadRepository extends JpaRepository<Upload, Long> {
    List<Upload> findByUserEmail(String userEmail);
}