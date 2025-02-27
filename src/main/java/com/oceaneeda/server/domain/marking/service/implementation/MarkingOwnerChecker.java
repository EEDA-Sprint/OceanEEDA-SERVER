package com.oceaneeda.server.domain.marking.service.implementation;

import com.oceaneeda.server.domain.auth.service.annotation.ForEntity;
import com.oceaneeda.server.domain.auth.service.core.OwnerChecker;
import com.oceaneeda.server.domain.marking.domain.Marking;
import com.oceaneeda.server.domain.marking.domain.repository.MarkingRepository;
import com.oceaneeda.server.domain.marking.exception.NotMatchMarkingWriterException;
import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.domain.value.Role;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@ForEntity(Marking.class)
@RequiredArgsConstructor
public class MarkingOwnerChecker implements OwnerChecker<ObjectId> {
    private final MarkingRepository markingRepository;

    @Override
    public void checkOwner(ObjectId markingId, User user) {
        if (user.getRole() != Role.ADMIN && !markingRepository.existsByIdAndUserId(markingId, user.getId())) {
            throw new NotMatchMarkingWriterException();
        }
    }
}