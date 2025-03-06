package com.oceaneeda.server.domain.marking.presentation.dto.request;

import com.oceaneeda.server.domain.marking.domain.Marking;
import com.oceaneeda.server.domain.marking.domain.value.Category;
import org.bson.types.ObjectId;

import java.util.Collections;
import java.util.List;

public record MarkingInput(
        ObjectId regionId,
        String title,
        String content,
        String poster,
        List<String> trashTypes,
        GeoJsonPointInput location,
        List<FileInput> files,
        Boolean isApproved,
        Category category
) {
    public Marking toEntity() {
        return Marking.builder()
                .regionId(regionId)
                .title(title)
                .content(content)
                .poster(poster)
                .trashTypes(trashTypes)
                .location(location != null ? location.toGeoJsonPoint() : null)
                .files(files != null ? files.stream()
                        .map(FileInput::toDomain)
                        .toList() : Collections.emptyList())
                .isApproved(isApproved)
                .category(category)
                .build();
    }
}