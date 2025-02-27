package com.oceaneeda.server.domain.file.presentation.dto.response;

import java.util.List;

public record PathResponse (
        List<String> path
) {
    public static PathResponse from(List<String> path) {
        return new PathResponse(path);
    }
}
