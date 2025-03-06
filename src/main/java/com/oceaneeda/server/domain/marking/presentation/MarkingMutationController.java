package com.oceaneeda.server.domain.marking.presentation;

import com.oceaneeda.server.domain.auth.annotation.Authenticated;
import com.oceaneeda.server.domain.auth.annotation.OwnerOnly;
import com.oceaneeda.server.domain.auth.service.implementation.AuthReader;
import com.oceaneeda.server.domain.marking.domain.Marking;
import com.oceaneeda.server.domain.marking.presentation.dto.request.MarkingInput;
import com.oceaneeda.server.domain.marking.presentation.dto.response.MarkingResponse;
import com.oceaneeda.server.domain.marking.service.CommandMarkingService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class MarkingMutationController {

    private final CommandMarkingService commandMarkingService;
    private final AuthReader authReader;

    @Authenticated
    @MutationMapping
    public MarkingResponse createMarking(@Argument MarkingInput input) {
        return MarkingResponse.from(commandMarkingService.createMarking(input.toEntity(), authReader.getCurrentUser()));
    }

    @OwnerOnly(resourceType = Marking.class)
    @MutationMapping
    public MarkingResponse updateMarking(@Argument ObjectId id, @Argument MarkingInput input) {
        return MarkingResponse.from(commandMarkingService.updateMarking(id, input.toEntity()));
    }

    @OwnerOnly(resourceType = Marking.class)
    @MutationMapping
    public MarkingResponse deleteMarking(@Argument ObjectId id) {
        return MarkingResponse.from(commandMarkingService.deleteMarking(id));
    }
}