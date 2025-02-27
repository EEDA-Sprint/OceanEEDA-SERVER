package com.oceaneeda.server.domain.file.service.implementation;

import com.oceaneeda.server.domain.file.domain.File;
import com.oceaneeda.server.domain.file.exception.FileDeleteFailedException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileDeleter {

    public void deleteByMarking(List<File> files) {
        for (File file : files) {
            delete(file.getPath());
        }
    }

    private void delete(String path) {
        Path filePath = Paths.get(path);

        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new FileDeleteFailedException();
        }
    }
}
