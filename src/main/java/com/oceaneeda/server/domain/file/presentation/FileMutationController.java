package com.oceaneeda.server.domain.file.presentation;

import com.oceaneeda.server.domain.file.presentation.dto.request.PathInput;
import com.oceaneeda.server.domain.file.presentation.dto.response.PathResponse;
import com.oceaneeda.server.domain.file.service.CommandFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class FileMutationController {

    private final CommandFileService commandFileService;

    @MutationMapping
    public PathResponse uploadFile(@Argument("input") PathInput input) {
        return PathResponse.from(commandFileService.uploadFile(input.files()));
    }
}
