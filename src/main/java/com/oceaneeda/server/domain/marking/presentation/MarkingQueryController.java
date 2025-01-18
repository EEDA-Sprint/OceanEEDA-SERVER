package com.oceaneeda.server.domain.marking.presentation;

import com.oceaneeda.server.domain.auth.repository.AuthRepository;
import com.oceaneeda.server.domain.marking.domain.Marking;
import com.oceaneeda.server.domain.marking.domain.repository.MarkingRepository;
import com.oceaneeda.server.domain.marking.presentation.dto.response.MarkingResponse;
import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.domain.value.Role;
import com.oceaneeda.server.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MarkingQueryController {

    private final MarkingRepository markingRepository;
    private final AuthRepository authRepository;

    @QueryMapping
    public MarkingResponse getMarkingById(@Argument ObjectId id) {
        User user = authRepository.getNullableCurrentUser();
        Marking marking = markingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marking not found by id: " + id));

        if (user == null) {
            if (!Boolean.TRUE.equals(marking.getIsApproved())) {
                throw new EntityNotFoundException("Marking not found or not accessible for non-authenticated users.");
            }
        } else if (user.getRole() != Role.ROLE_ADMIN) {
            if (!Boolean.TRUE.equals(marking.getIsApproved()) && !marking.getUserId().equals(user.getId())) {
                throw new EntityNotFoundException("Marking not found or not accessible for the current user.");
            }
        }
        return MarkingResponse.from(marking);
    }

    @QueryMapping
    public List<MarkingResponse> getAllMarkings() {
        User user = authRepository.getNullableCurrentUser();
        List<Marking> markingList;

        if (user == null) {
            markingList = markingRepository.findByIsApprovedTrue();
        } else if (user.getRole() == Role.ROLE_ADMIN) {
            markingList = markingRepository.findAll();
        } else {
            markingList = markingRepository.findByIsApprovedTrueOrUserId(user.getId().toHexString());
        }

        return markingList.stream()
                .map(MarkingResponse::from)
                .toList();
    }
}