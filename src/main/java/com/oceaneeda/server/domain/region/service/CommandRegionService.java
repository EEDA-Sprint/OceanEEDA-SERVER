package com.oceaneeda.server.domain.region.service;

import com.oceaneeda.server.domain.region.domain.Region;
import com.oceaneeda.server.domain.region.service.implementation.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommandRegionService {

    private final RegionCreator regionCreator;
    private final RegionReader regionReader;
    private final RegionUpdater regionUpdater;
    private final RegionDeleter regionDeleter;
    private final RegionValidator regionValidator;

    public Region createRegion(Region region) {
        regionValidator.shouldNotExistName(region);
        return regionCreator.create(region);
    }

    public Region updateRegion(ObjectId regionId, Region region) {
        regionValidator.shouldNotExistName(region);
        Region updatableRegion = regionReader.read(regionId);
        return regionUpdater.update(updatableRegion, region);
    }

    public Region deleteRegion(ObjectId regionId) {
        Region deletableRegion = regionReader.read(regionId);
        regionDeleter.delete(deletableRegion);
        return deletableRegion;
    }
}
