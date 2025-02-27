package com.oceaneeda.server.domain.file.service;

import com.oceaneeda.server.domain.file.service.implementation.DirectoryCreator;
import com.oceaneeda.server.domain.file.service.implementation.FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommandFileService {

    private final FileUploader fileUploader;
    private final DirectoryCreator directoryCreator;

    public List<String> uploadFile(List<MultipartFile> files) {

        Path uploadPath = directoryCreator.createUploadDir();

        return files.stream()
                .map(file -> fileUploader.upload(file, uploadPath))
                .toList();
    }
}


