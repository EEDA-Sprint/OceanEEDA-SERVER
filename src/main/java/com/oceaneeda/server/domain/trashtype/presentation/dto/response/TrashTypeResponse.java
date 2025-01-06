package com.oceaneeda.server.domain.trashtype.presentation.dto.response;

import com.oceaneeda.server.domain.trashtype.domain.TrashType;
import lombok.Builder;
import org.bson.types.ObjectId;

@Builder
public record TrashTypeResponse(
        ObjectId id,
        String name
) {
    public static TrashTypeResponse from(TrashType trashType) {
        return TrashTypeResponse.builder()
                .id(trashType.getId())
                .name(trashType.getName())
                .build();
    }
}
