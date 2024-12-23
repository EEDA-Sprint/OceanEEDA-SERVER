package com.oceaneeda.server.domain.marking.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir; // 파일 저장 경로를 application.properties에서 설정합니다.

    public String storeFile(MultipartFile file) throws IOException {
        // 업로드된 파일 이름과 확장자를 분리
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // 고유 파일 이름 생성
        String uniqueFileName = UUID.randomUUID() + fileExtension;

        // 저장 경로 결정
        Path targetLocation = Paths.get(uploadDir).resolve(uniqueFileName);

        // 디렉토리가 없으면 생성
        Files.createDirectories(targetLocation.getParent());

        // 파일 저장
        Files.copy(file.getInputStream(), targetLocation);

        return targetLocation.toString(); // 저장 경로 반환
    }
}