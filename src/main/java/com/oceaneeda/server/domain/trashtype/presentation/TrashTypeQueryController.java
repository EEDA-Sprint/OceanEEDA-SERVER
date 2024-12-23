package com.oceaneeda.server.domain.trashtype.presentation;

import com.oceaneeda.server.domain.trashtype.domain.TrashType;
import com.oceaneeda.server.domain.trashtype.domain.repository.TrashTypeRepository;
import com.oceaneeda.server.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TrashTypeQueryController {

    private final TrashTypeRepository trashTypeRepository;

    // Query: 특정 TrashType 조회
    @QueryMapping
    public TrashType getTrashTypeById(@Argument ObjectId id) {
        return trashTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TrashType not found by id: " + id));
    }

    // Query: 전체 TrashTypes 조회
    @QueryMapping
    public List<TrashType> getAllTrashTypes() {
        return trashTypeRepository.findAll();
    }
}