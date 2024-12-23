package com.oceaneeda.server.domain.marking.presentation;

import com.oceaneeda.server.domain.marking.domain.Marking;
import com.oceaneeda.server.domain.marking.domain.repository.MarkingRepository;
import com.oceaneeda.server.domain.marking.presentation.dto.response.MarkingResponse;
import com.oceaneeda.server.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MarkingQueryController {

    private final MarkingRepository markingRepository;

    @QueryMapping
    public MarkingResponse getMarkingById(@Argument ObjectId id) {
        return markingRepository.findById(id)
                .map(MarkingResponse::from)
                .orElseThrow(() -> new EntityNotFoundException("Marking not found by id: " + id));
    }

    @QueryMapping
    public List<MarkingResponse> getAllMarkings() {
        return markingRepository.findAll()
                .stream()
                .map(MarkingResponse::from)
                .toList();
    }
}