package com.oceaneeda.server.domain.file.presentation.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record PathInput(
        List<MultipartFile> files
) {
}
