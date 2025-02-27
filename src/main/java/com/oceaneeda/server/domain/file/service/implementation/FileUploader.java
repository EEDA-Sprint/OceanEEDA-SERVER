package com.oceaneeda.server.domain.file.service.implementation;

import com.oceaneeda.server.domain.file.exception.FileUploadFailedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileUploader {

    public String upload(MultipartFile file, Path uploadPath) {
        String fileName = createFileName(file);
        Path targetLocation = uploadPath.resolve(fileName);

        try {
            Files.copy(file.getInputStream(), targetLocation);
        } catch (IOException e) {
            throw new FileUploadFailedException();
        }

        return targetLocation.toString();
    }

    private String createFileName(MultipartFile file) {
        return file.getOriginalFilename() + "_" + UUID.randomUUID();
    }
}