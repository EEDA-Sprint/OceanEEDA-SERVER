package com.oceaneeda.server.domain.region.domain.repository;

import com.oceaneeda.server.domain.region.domain.Region;
import com.oceaneeda.server.domain.region.exception.RegionNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends MongoRepository<Region, ObjectId> {

    boolean existsByName(String name);

    default Region getById(ObjectId regionId) {
        return findById(regionId)
                .orElseThrow(() -> new RegionNotFoundException(regionId));
    }
}
