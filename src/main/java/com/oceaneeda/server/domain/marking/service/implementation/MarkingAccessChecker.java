package com.oceaneeda.server.domain.marking.service.implementation;

import com.oceaneeda.server.domain.auth.service.annotation.ForEntity;
import com.oceaneeda.server.domain.auth.service.core.AccessChecker;
import com.oceaneeda.server.domain.marking.domain.Marking;
import com.oceaneeda.server.domain.marking.domain.repository.MarkingRepository;
import com.oceaneeda.server.domain.marking.exception.MarkingNotFoundException;
import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.domain.value.Role;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@ForEntity(Marking.class)
@RequiredArgsConstructor
public class MarkingAccessChecker implements AccessChecker<ObjectId> {
    private final MarkingRepository markingRepository;

    @Override
    public void checkAccess(ObjectId markingId, User user) {
        Marking marking = markingRepository.getById(markingId);

        if (user == null) {
            checkGuestAccess(marking);
            return;
        }

        if (user.getRole() == Role.ADMIN) {
            return;
        }

        checkUserAccess(marking, user);
    }

    private void checkGuestAccess(Marking marking) {
        if (!marking.getIsApproved()) {
            throw new MarkingNotFoundException(marking.getId());
        }
    }

    private void checkUserAccess(Marking marking, User user) {
        if (marking.getIsApproved()) {
            return;
        }

        if (marking.getUserId().equals(user.getId())) {
            return;
        }

        throw new MarkingNotFoundException(marking.getId());
    }

}
