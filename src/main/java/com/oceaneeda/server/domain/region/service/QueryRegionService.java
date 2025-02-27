package com.oceaneeda.server.domain.region.service;

import com.oceaneeda.server.domain.region.domain.Region;
import com.oceaneeda.server.domain.region.service.implementation.RegionReader;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QueryRegionService {

    private final RegionReader regionReader;

    public Region readOne(ObjectId regionId) {
        return regionReader.read(regionId);
    }

    public List<Region> readAll() {
        return regionReader.readAll();
    }
}
