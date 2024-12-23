package com.oceaneeda.server.domain.marking.presentation.dto.request;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

public record GeoJsonPointInput(
        Float latitude,
        Float longitude
) {
    public GeoJsonPoint toGeoJsonPoint() {
        return new GeoJsonPoint(longitude, latitude); // GeoJsonPoint는 (x: longitude, y: latitude) 순서로 생성됩니다.
    }
}