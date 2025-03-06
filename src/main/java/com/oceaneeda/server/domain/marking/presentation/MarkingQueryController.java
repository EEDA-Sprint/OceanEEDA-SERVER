package com.oceaneeda.server.domain.marking.presentation;

import com.oceaneeda.server.domain.auth.annotation.LoginOrNot;
import com.oceaneeda.server.domain.auth.annotation.PublicOrOwner;
import com.oceaneeda.server.domain.auth.service.implementation.AuthReader;
import com.oceaneeda.server.domain.marking.domain.Marking;
import com.oceaneeda.server.domain.marking.presentation.dto.response.MarkingResponse;
import com.oceaneeda.server.domain.marking.service.QueryMarkingService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MarkingQueryController {

    private final QueryMarkingService queryMarkingService;
    private final AuthReader authReader;

    @PublicOrOwner(resourceType = Marking.class)
    @QueryMapping
    public MarkingResponse getMarkingById(@Argument ObjectId id) {
        return MarkingResponse.from(queryMarkingService.readOne(id));
    }

    @LoginOrNot
    @QueryMapping
    public List<MarkingResponse> getAllMarkings() {
        return queryMarkingService.readAll(authReader.getNullableCurrentUser()).stream()
                .map(MarkingResponse::from)
                .toList();
    }
}