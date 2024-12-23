package com.oceaneeda.server.domain.region.presentation;

import com.oceaneeda.server.domain.region.domain.Region;
import com.oceaneeda.server.domain.region.domain.repository.RegionRepository;
import com.oceaneeda.server.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RegionQueryController {

    private final RegionRepository regionRepository;

    // Query: 특정 Region 조회
    @QueryMapping
    public Region getRegionById(@Argument ObjectId id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Region not found by id: " + id));
    }

    // Query: 전체 Region 조회
    @QueryMapping
    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }
}