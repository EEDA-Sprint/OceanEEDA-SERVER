package com.oceaneeda.server.domain.file.presentation.dto.response;

import java.util.List;

public record MultipartFileResponse(
        List<String> paths
) {
    public static MultipartFileResponse from(List<String> paths) {
        return new MultipartFileResponse(paths);
    }
}
