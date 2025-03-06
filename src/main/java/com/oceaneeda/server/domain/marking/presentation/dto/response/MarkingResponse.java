package com.oceaneeda.server.domain.marking.presentation.dto.response;

import com.oceaneeda.server.domain.marking.domain.Marking;
import com.oceaneeda.server.domain.marking.domain.value.Category;
import lombok.Builder;
import org.bson.types.ObjectId;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Builder
public record MarkingResponse (
        ObjectId id,
        ObjectId userId,
        ObjectId regionId,
        String title,
        String content,
        String poster,
        List<String> trashTypes,
        GeoJsonPointResponse location,
        List<FileResponse> files,
        OffsetDateTime createdAt,
        Boolean isApproved,
        Category category
) {
    public static MarkingResponse from(Marking marking) {
        return MarkingResponse.builder()
                .id(marking.getId())
                .userId(marking.getUserId())
                .regionId(marking.getRegionId())
                .title(marking.getTitle())
                .content(marking.getContent())
                .poster(marking.getPoster())
                .trashTypes(marking.getTrashTypes())
                .location(marking.getLocation() != null ?
                        GeoJsonPointResponse.from(marking.getLocation()) : null)
                .files(marking.getFiles() != null ?
                        marking.getFiles().stream()
                                .map(FileResponse::from)
                                .toList() : List.of())
                .createdAt(marking.getCreatedAt().atOffset(ZoneOffset.UTC))
                .isApproved(marking.getIsApproved())
                .category(marking.getCategory())
                .build();
    }
}
