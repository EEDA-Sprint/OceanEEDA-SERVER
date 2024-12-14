package com.oceaneeda.server.domain.marking.service;

import com.oceaneeda.server.domain.marking.domain.Marking;
import com.oceaneeda.server.domain.marking.domain.repository.MarkingRepository;
import com.oceaneeda.server.domain.marking.domain.value.Category;
import com.oceaneeda.server.domain.marking.presentation.dto.request.MarkingInput;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MarkingService {

    private final MarkingRepository markingRepository;
    private final FileStorageService fileStorageService;

    public MarkingService(MarkingRepository markingRepository, FileStorageService fileStorageService) {
        this.markingRepository = markingRepository;
        this.fileStorageService = fileStorageService;
    }

    public Marking createMarking(MarkingInput markingInput) throws IOException {
        // 파일 저장 처리
        List<Marking.File> uploadedFiles = new ArrayList<>();
        if (markingInput.getFiles() != null) {
            uploadedFiles = markingInput.getFiles().stream()
                    .map(file -> {
                        try {
                            String filePath = fileStorageService.storeFile(file);
                            Marking.File markingFile = new Marking.File();
                            markingFile.setName(file.getOriginalFilename());
                            markingFile.setPath(filePath);
                            return markingFile;
                        } catch (IOException e) {
                            throw new RuntimeException("File upload failed", e);
                        }
                    })
                    .collect(Collectors.toList());
        }

        // Marking 생성 로직
        Marking marking = new Marking();
        marking.setUserId(markingInput.getUserId());
        marking.setRegionId(markingInput.getRegionId());
        marking.setTitle(markingInput.getTitle());
        marking.setContent(markingInput.getContent());
        marking.setPoster(markingInput.getPoster());
        marking.setTrashTypes(markingInput.getTrashTypes());
        marking.setIsApproved(markingInput.getIsApproved());

        // GeoPoint 설정
        if (markingInput.getLatitude() != null && markingInput.getLongitude() != null) {
            marking.setLocation(new GeoJsonPoint(markingInput.getLongitude(), markingInput.getLatitude()));
        }

        // Category 설정
        if (markingInput.getCategory() != null) {
            marking.setCategory(Category.valueOf(markingInput.getCategory()));
        }

        marking.setFiles(uploadedFiles);
        return markingRepository.save(marking);
    }

    public List<Marking.File> uploadFiles(List<MultipartFile> files) throws IOException {
        if (files == null || files.isEmpty()) {
            return List.of();
        }

        final String UPLOAD_DIR = "uploads/";
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        return files.stream().map(file -> {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            try {
                Files.write(uploadPath.resolve(fileName), file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload file: " + file.getOriginalFilename(), e);
            }

            Marking.File markingFile = new Marking.File();
            markingFile.setName(file.getOriginalFilename());
            markingFile.setPath(UPLOAD_DIR + fileName);
            return markingFile;
        }).collect(Collectors.toList());
    }

    public Marking updateMarking(ObjectId id, MarkingInput markingInput) {
        Optional<Marking> optionalMarking = markingRepository.findById(id);
        if (optionalMarking.isEmpty()) {
            throw new IllegalArgumentException("Marking not found");
        }
        Marking marking = optionalMarking.get();
        marking.setTitle(markingInput.getTitle());
        marking.setContent(markingInput.getContent());
        marking.setIsApproved(markingInput.getIsApproved());
        marking.setTrashTypes(markingInput.getTrashTypes());
        marking.setCategory(Category.valueOf(markingInput.getCategory()));
        return markingRepository.save(marking);
    }

    public boolean deleteMarking(ObjectId id) {
        if (!markingRepository.existsById(id)) {
            throw new IllegalArgumentException("Marking not found");
        }
        markingRepository.deleteById(id);
        return true;
    }

    public Marking getMarkingById(ObjectId id) {
        return markingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Marking not found"));
    }

    public List<Marking> getAllMarkings() {
        return markingRepository.findAll();
    }
}