package com.oceaneeda.server.domain.marking.domain.repository;

import com.oceaneeda.server.domain.marking.domain.Marking;
import com.oceaneeda.server.domain.marking.exception.MarkingNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkingRepository extends MongoRepository<Marking, ObjectId> {
    boolean existsByIdAndUserId(ObjectId id, ObjectId userId);

    List<Marking> findByIsApprovedTrue();

    List<Marking> findByUserId(ObjectId userId);

    default Marking getById(ObjectId markingId) {
        return findById(markingId)
                .orElseThrow(() -> new MarkingNotFoundException(markingId));
    }
}
