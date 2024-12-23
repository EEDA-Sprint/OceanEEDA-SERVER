package com.oceaneeda.server.domain.marking.presentation.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record FileInput (
        String name,
        MultipartFile file
) {
}
