package com.jobportal.service;

import com.jobportal.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path resumeDir;

    public FileStorageService(@Value("${app.upload-dir}") String uploadDir) {
        this.resumeDir = Paths.get(uploadDir, "resumes").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.resumeDir);
        } catch (IOException e) {
            throw new IllegalStateException("Could not create upload folder", e);
        }
    }

    public String saveResume(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ApiException("Please choose a resume file", 400);
        }
        String original = file.getOriginalFilename() == null ? "resume" : file.getOriginalFilename();
        String lower = original.toLowerCase();
        if (!(lower.endsWith(".pdf") || lower.endsWith(".doc") || lower.endsWith(".docx"))) {
            throw new ApiException("Only PDF, DOC and DOCX files are allowed", 400);
        }
        String stored = UUID.randomUUID() + "-" + original.replaceAll("[^a-zA-Z0-9._-]", "_");
        try {
            Files.copy(file.getInputStream(), resumeDir.resolve(stored), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ApiException("Failed to save resume", 500);
        }
        return stored;
    }
}
