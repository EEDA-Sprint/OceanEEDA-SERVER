package com.oceaneeda.server.domain.file.presentation;

import com.oceaneeda.server.domain.file.presentation.dto.request.MultipartFileInput;
import com.oceaneeda.server.domain.file.presentation.dto.response.MultipartFileResponse;
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
    public MultipartFileResponse uploadFile(@Argument("input") MultipartFileInput input) {
        return MultipartFileResponse.from(commandFileService.uploadFile(input.files()));
    }
}
