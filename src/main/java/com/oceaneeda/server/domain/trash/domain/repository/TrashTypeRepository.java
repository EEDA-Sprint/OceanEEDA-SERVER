package com.oceaneeda.server.domain.trash.domain.repository;

import com.oceaneeda.server.domain.trash.domain.TrashType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrashTypeRepository extends MongoRepository<TrashType, ObjectId> {
}
