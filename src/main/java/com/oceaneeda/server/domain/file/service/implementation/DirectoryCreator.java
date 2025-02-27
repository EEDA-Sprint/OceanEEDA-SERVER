package com.oceaneeda.server.domain.file.service.implementation;

import com.oceaneeda.server.domain.file.exception.DirectoryCreationFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DirectoryCreator {
    @Value("${file.upload-dir}")
    private String uploadDir;

    public Path createUploadDir() {
        Path dir = Paths.get(uploadDir);

        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            throw new DirectoryCreationFailedException();
        }

        return dir;
    }
}
