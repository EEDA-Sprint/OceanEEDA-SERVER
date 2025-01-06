package com.oceaneeda.server.domain.region.presentation.dto.response;

import com.oceaneeda.server.domain.region.domain.Region;
import lombok.Builder;
import org.bson.types.ObjectId;

@Builder
public record RegionResponse(
        ObjectId id,
        String name
) {
    public static RegionResponse from(Region region) {
        return RegionResponse.builder()
                .id(region.getId())
                .name(region.getName())
                .build();
    }
}
