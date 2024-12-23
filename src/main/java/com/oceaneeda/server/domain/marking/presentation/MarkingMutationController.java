package com.oceaneeda.server.domain.marking.presentation;

import com.oceaneeda.server.domain.marking.domain.Marking;
import com.oceaneeda.server.domain.marking.domain.repository.MarkingRepository;
import com.oceaneeda.server.domain.marking.presentation.dto.request.CreateMarkingInput;
import com.oceaneeda.server.domain.marking.presentation.dto.request.FileInput;
import com.oceaneeda.server.domain.marking.presentation.dto.request.UpdateMarkingInput;
import com.oceaneeda.server.domain.marking.presentation.dto.response.MarkingResponse;
import com.oceaneeda.server.domain.marking.service.FileStorageService;
import com.oceaneeda.server.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MarkingMutationController {

    private final MarkingRepository markingRepository;
    private final FileStorageService fileStorageService;

    @MutationMapping
    public MarkingResponse createMarking(@Argument("input") CreateMarkingInput input) throws IOException {
        Marking marking = new Marking();
        marking.setId(new ObjectId());
        marking.setUserId(input.userId());
        marking.setRegionId(input.regionId());
        marking.setTitle(input.title());
        marking.setContent(input.content());
        marking.setPoster(input.poster());
        marking.setTrashTypes(input.trashTypes());
        marking.setLocation(input.location().toGeoJsonPoint());
        marking.setIsApproved(input.isApproved());
        marking.setCategory(input.category());
        marking.setCreatedAt(LocalDateTime.now());

        // 파일 저장 로직
        List<Marking.File> savedFiles = new ArrayList<>();
        if (input.files() != null) {
            for (FileInput file : input.files()) {
                String filePath = fileStorageService.storeFile(file.file());
                Marking.File markingFile = new Marking.File();
                markingFile.setName(file.name());
                markingFile.setPath(filePath);
                savedFiles.add(markingFile);
            }
        }

        marking.setFiles(savedFiles);

        return MarkingResponse.from(markingRepository.save(marking));
    }

    @MutationMapping
    public MarkingResponse updateMarking(@Argument ObjectId id,
                                 @Argument("input") UpdateMarkingInput input) throws IOException {
        Marking marking = markingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marking not found by id: " + id));

        if (input.regionId() != null) marking.setRegionId(input.regionId());
        if (input.title() != null) marking.setTitle(input.title());
        if (input.content() != null) marking.setContent(input.content());
        if (input.poster() != null) marking.setPoster(input.poster());
        if (input.trashTypes() != null) marking.setTrashTypes(input.trashTypes());
        if (input.location() != null) marking.setLocation(input.location().toGeoJsonPoint());
        if (input.isApproved() != null) marking.setIsApproved(input.isApproved());
        if (input.category() != null) marking.setCategory(input.category());

        // 파일 저장 로직
        List<Marking.File> savedFiles = new ArrayList<>();
        if (input.files() != null) {
            for (FileInput file : input.files()) {
                String filePath = fileStorageService.storeFile(file.file());
                Marking.File markingFile = new Marking.File();
                markingFile.setName(file.name());
                markingFile.setPath(filePath);
                savedFiles.add(markingFile);
            }
        }
        marking.setFiles(savedFiles);

        return MarkingResponse.from(markingRepository.save(marking));
    }

    // DELETE
    @MutationMapping
    public Boolean deleteMarking(@Argument ObjectId id) {
        if (!markingRepository.existsById(id)) {
            throw new EntityNotFoundException("Marking not found by id: " + id);
        }
        markingRepository.deleteById(id);
        return true;
    }
}