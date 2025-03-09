package com.oceaneeda.server.domain.marking.service.implementation;

import com.oceaneeda.server.domain.auth.exception.UserNotAdminException;
import com.oceaneeda.server.domain.auth.service.annotation.ForField;
import com.oceaneeda.server.domain.auth.service.core.FieldChecker;
import com.oceaneeda.server.domain.marking.presentation.dto.request.MarkingInput;
import com.oceaneeda.server.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.oceaneeda.server.domain.user.domain.value.Role.ADMIN;

@Component
@ForField(inputType = MarkingInput.class, fieldName = "isApproved")
@RequiredArgsConstructor
public class MarkingIsApprovedFieldChecker implements FieldChecker<MarkingInput> {
    @Override
    public void checkField(MarkingInput input, User currentUser) {
        if (input.isApproved() != null && currentUser.getRole() != ADMIN) {
            throw new UserNotAdminException();
        }
    }
}
