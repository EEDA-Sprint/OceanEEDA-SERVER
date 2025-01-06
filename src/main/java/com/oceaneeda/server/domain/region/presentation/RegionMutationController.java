package com.oceaneeda.server.domain.region.presentation;

import com.oceaneeda.server.domain.auth.annotation.AdminOnly;
import com.oceaneeda.server.domain.region.domain.Region;
import com.oceaneeda.server.domain.region.domain.repository.RegionRepository;
import com.oceaneeda.server.domain.region.presentation.dto.request.CreateRegionInput;
import com.oceaneeda.server.domain.region.presentation.dto.request.UpdateRegionInput;
import com.oceaneeda.server.domain.region.presentation.dto.response.RegionResponse;
import com.oceaneeda.server.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class RegionMutationController {

    private final RegionRepository regionRepository;

    // Mutation: Region 생성
    @AdminOnly
    @MutationMapping
    public RegionResponse createRegion(@Argument("input") CreateRegionInput input) {
        Region newRegion = new Region();
        newRegion.setId(new ObjectId()); // MongoDB ID 생성
        newRegion.setName(input.name());

        return RegionResponse.from(regionRepository.save(newRegion));
    }

    // Mutation: Region 정보 수정
    @AdminOnly
    @MutationMapping
    public RegionResponse updateRegion(@Argument ObjectId id, @Argument("input") UpdateRegionInput input) {
        Region existingRegion = regionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Region not found by id: " + id));

        if (input.name() != null) {
            existingRegion.setName(input.name());
        }

        return RegionResponse.from(regionRepository.save(existingRegion));
    }

    // Mutation: Region 삭제
    @AdminOnly
    @MutationMapping
    public RegionResponse deleteRegion(@Argument ObjectId id) {
        Region deletedRegion = regionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Region not found by id: " + id));

        regionRepository.delete(deletedRegion);
        return RegionResponse.from(deletedRegion);
    }
}