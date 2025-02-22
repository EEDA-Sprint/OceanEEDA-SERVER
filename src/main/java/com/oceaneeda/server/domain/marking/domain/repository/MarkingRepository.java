package com.oceaneeda.server.domain.marking.domain.repository;

import com.oceaneeda.server.domain.marking.domain.Marking;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkingRepository extends MongoRepository<Marking, ObjectId> {
    boolean existsByIdAndUserId(ObjectId id, String userId);

    List<Marking> findByIsApprovedTrue();

    List<Marking> findByIsApprovedTrueOrUserId(String userId);
}
