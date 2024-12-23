package com.oceaneeda.server.domain.trashtype.presentation;

import com.oceaneeda.server.domain.trashtype.domain.TrashType;
import com.oceaneeda.server.domain.trashtype.domain.repository.TrashTypeRepository;
import com.oceaneeda.server.domain.trashtype.presentation.dto.request.CreateTrashTypeInput;
import com.oceaneeda.server.domain.trashtype.presentation.dto.request.UpdateTrashTypeInput;
import com.oceaneeda.server.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TrashTypeMutationController {

    private final TrashTypeRepository trashTypeRepository;

    // Mutation: TrashType 생성
    @MutationMapping
    public TrashType createTrashType(@Argument("input") CreateTrashTypeInput input) {
        TrashType newTrashType = new TrashType();
        newTrashType.setId(new ObjectId()); // MongoDB ID 생성
        newTrashType.setName(input.name());

        return trashTypeRepository.save(newTrashType);
    }

    // Mutation: TrashType 정보 수정
    @MutationMapping
    public TrashType updateTrashType(@Argument ObjectId id, @Argument("input") UpdateTrashTypeInput input) {
        TrashType existingTrashType = trashTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TrashType not found by id: " + id));

        if (input.name() != null) {
            existingTrashType.setName(input.name());
        }

        return trashTypeRepository.save(existingTrashType);
    }

    // Mutation: TrashType 삭제
    @MutationMapping
    public Boolean deleteTrashType(@Argument ObjectId id) {
        Optional<TrashType> trashTypeToDelete = trashTypeRepository.findById(id);

        if (trashTypeToDelete.isEmpty()) {
            return false;
        }

        trashTypeRepository.deleteById(id);
        return true;
    }
}