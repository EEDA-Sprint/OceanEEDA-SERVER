package com.oceaneeda.server.domain.marking.presentation.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MarkingInput {
    private String userId;
    private String regionId;
    private String title;
    private String content;
    private String poster;
    private List<String> trashTypes;
    private Double latitude;
    private Double longitude;
    private Boolean isApproved;
    private String category; // Enum Category
    private List<MultipartFile> files; // 업로드된 파일
}
