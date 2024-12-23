package com.oceaneeda.server.domain.trashtype.domain.repository;

import com.oceaneeda.server.domain.trashtype.domain.TrashType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrashTypeRepository extends MongoRepository<TrashType, ObjectId> {
}
