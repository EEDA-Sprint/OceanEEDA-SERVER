package com.oceaneeda.server.domain.region.service.implementation;

import com.oceaneeda.server.domain.region.domain.Region;
import com.oceaneeda.server.domain.region.domain.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionCreator {
    private final RegionRepository regionRepository;

    public Region create(Region region) {
        return regionRepository.save(region);
    }
}
