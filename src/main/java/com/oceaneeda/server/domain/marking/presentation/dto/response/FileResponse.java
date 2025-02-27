package com.oceaneeda.server.domain.marking.presentation.dto.response;

import com.oceaneeda.server.domain.file.domain.File;
import lombok.Builder;

@Builder
public record FileResponse(
        String name,
        String path,
        int order
) {
    public static FileResponse from(File file) {
        return FileResponse.builder()
                .name(file.getName())
                .path(file.getPath())
                .order(file.getOrder())
                .build();
    }
}
