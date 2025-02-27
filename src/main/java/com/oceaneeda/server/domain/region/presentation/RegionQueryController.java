package com.oceaneeda.server.domain.region.presentation;

import com.oceaneeda.server.domain.region.presentation.dto.response.RegionResponse;
import com.oceaneeda.server.domain.region.service.QueryRegionService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RegionQueryController {

    private final QueryRegionService queryRegionService;

    @QueryMapping
    public RegionResponse getRegionById(@Argument ObjectId id) {
        return RegionResponse.from(queryRegionService.readOne(id));
    }

    @QueryMapping
    public List<RegionResponse> getAllRegions() {
        return queryRegionService.readAll().stream()
                .map(RegionResponse::from)
                .toList();
    }
}