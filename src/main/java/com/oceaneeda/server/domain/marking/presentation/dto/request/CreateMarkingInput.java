package com.oceaneeda.server.domain.marking.presentation.dto.request;

import com.oceaneeda.server.domain.marking.domain.value.Category;

import java.util.List;

public record CreateMarkingInput(
        String userId,
        String regionId,
        String title,
        String content,
        String poster,
        List<String> trashTypes,
        GeoJsonPointInput location,
        List<FileInput> files,
        Boolean isApproved,
        Category category
) {}