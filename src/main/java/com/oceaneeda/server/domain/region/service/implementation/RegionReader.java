package com.oceaneeda.server.domain.region.service.implementation;

import com.oceaneeda.server.domain.region.domain.Region;
import com.oceaneeda.server.domain.region.domain.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionReader {
    private final RegionRepository regionRepository;

    public Region read(ObjectId regionId) {
        return regionRepository.getById(regionId);
    }

    public List<Region> readAll() {
        return regionRepository.findAll();
    }
}
