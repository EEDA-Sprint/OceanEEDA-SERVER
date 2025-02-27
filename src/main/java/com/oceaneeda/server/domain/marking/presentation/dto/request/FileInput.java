package com.oceaneeda.server.domain.marking.presentation.dto.request;

import com.oceaneeda.server.domain.file.domain.File;

public record FileInput (
        String name,
        String path,
        int order
) {
    public File toDomain() {
        return File.builder()
                .name(name)
                .path(path)
                .order(order)
                .build();
    }
}
