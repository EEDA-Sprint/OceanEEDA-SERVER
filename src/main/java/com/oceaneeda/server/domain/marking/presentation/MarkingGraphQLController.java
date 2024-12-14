package com.oceaneeda.server.domain.marking.presentation;

import com.oceaneeda.server.domain.marking.domain.Marking;
import com.oceaneeda.server.domain.marking.presentation.dto.request.MarkingInput;
import com.oceaneeda.server.domain.marking.service.FileStorageService;
import com.oceaneeda.server.domain.marking.service.MarkingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MarkingGraphQLController {

    private final MarkingService markingService;
    private final FileStorageService fileStorageService;

    @QueryMapping
    public Marking getMarkingById(@Argument String id) {
        return markingService.getMarkingById(new ObjectId(id));
    }

    @QueryMapping
    public List<Marking> getAllMarkings() {
        return markingService.getAllMarkings();
    }

    @MutationMapping
    public Marking createMarking(@Argument MarkingInput markingInput) throws IOException {
        log.warn("createMarking : "+markingInput.toString());
        return markingService.createMarking(markingInput);
    }

    @MutationMapping
    public Marking updateMarking(@Argument String id, @Argument MarkingInput markingInput) {
        return markingService.updateMarking(new ObjectId(id), markingInput);
    }

    @MutationMapping
    public boolean deleteMarking(@Argument String id) {
        return markingService.deleteMarking(new ObjectId(id));
    }

    @MutationMapping
    public Marking.File uploadFile(@Argument MultipartFile file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("No file uploaded");
        }

        String filePath = fileStorageService.storeFile(file);

        Marking.File markingFile = new Marking.File();
        markingFile.setName(file.getOriginalFilename());
        markingFile.setPath(filePath);

        return markingFile;
    }
}