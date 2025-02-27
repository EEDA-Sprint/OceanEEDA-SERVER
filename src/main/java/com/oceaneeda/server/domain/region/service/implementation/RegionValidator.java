package com.oceaneeda.server.domain.region.service.implementation;

import com.oceaneeda.server.domain.region.domain.Region;
import com.oceaneeda.server.domain.region.domain.repository.RegionRepository;
import com.oceaneeda.server.domain.region.exception.RegionNameAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionValidator {
    private final RegionRepository regionRepository;

    public void shouldNotExistName(Region region) {
        if (regionRepository.existsByName(region.getName())) {
            throw new RegionNameAlreadyExistException(region.getName());
        }

    }

}
