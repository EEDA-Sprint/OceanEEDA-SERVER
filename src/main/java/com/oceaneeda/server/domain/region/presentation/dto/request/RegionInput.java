package com.oceaneeda.server.domain.region.presentation.dto.request;

import com.oceaneeda.server.domain.region.domain.Region;

public record RegionInput(
        String name
) {
    public Region toEntity() {
        return Region.builder()
                .name(name)
                .build();
    }
}
