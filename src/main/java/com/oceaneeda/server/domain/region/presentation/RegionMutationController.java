package com.oceaneeda.server.domain.region.presentation;

import com.oceaneeda.server.domain.auth.annotation.AdminOnly;
import com.oceaneeda.server.domain.auth.service.implementation.AuthReader;
import com.oceaneeda.server.domain.region.presentation.dto.request.RegionInput;
import com.oceaneeda.server.domain.region.presentation.dto.response.RegionResponse;
import com.oceaneeda.server.domain.region.service.CommandRegionService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class RegionMutationController {

    private final CommandRegionService commandRegionService;

    @AdminOnly
    @MutationMapping
    public RegionResponse createRegion(@Argument RegionInput input) {
        return RegionResponse.from(commandRegionService.createRegion(input.toEntity()));
    }

    @AdminOnly
    @MutationMapping
    public RegionResponse updateRegion(@Argument ObjectId id, @Argument RegionInput input) {
        return RegionResponse.from(commandRegionService.updateRegion(id, input.toEntity()));
    }

    @AdminOnly
    @MutationMapping
    public RegionResponse deleteRegion(@Argument ObjectId id) {
        return RegionResponse.from(commandRegionService.deleteRegion(id));
    }
}