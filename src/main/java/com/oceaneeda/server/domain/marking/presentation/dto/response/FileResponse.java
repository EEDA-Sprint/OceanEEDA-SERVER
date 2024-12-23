package com.oceaneeda.server.domain.marking.presentation.dto.response;

import com.oceaneeda.server.domain.marking.domain.Marking;
import lombok.Builder;

@Builder
public record FileResponse(
        String name,
        String path
) {
    public static FileResponse from(Marking.File file) {
        return FileResponse.builder()
                .name(file.getName())
                .path(file.getPath())
                .build();
    }
}
