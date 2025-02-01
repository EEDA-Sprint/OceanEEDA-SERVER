package com.oceaneeda.server.domain.marking.presentation.dto.response;

import lombok.Builder;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Builder
public record GeoJsonPointResponse(
        Float latitude,
        Float longitude
) {
    public static GeoJsonPointResponse from(GeoJsonPoint location) {
        return GeoJsonPointResponse.builder()
                .latitude((float) location.getY())
                .longitude((float) location.getX())
                .build();
    }
}
