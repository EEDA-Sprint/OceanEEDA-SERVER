package com.oceaneeda.server.domain.user.domain.repository;

import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.exception.UserNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    default User getById(ObjectId userId) {
        return findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
