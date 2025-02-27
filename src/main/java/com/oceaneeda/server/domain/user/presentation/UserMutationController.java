package com.oceaneeda.server.domain.user.presentation;

import com.oceaneeda.server.domain.auth.annotation.Authenticated;
import com.oceaneeda.server.domain.auth.service.implementation.AuthReader;
import com.oceaneeda.server.domain.user.presentation.dto.request.CreateUserInput;
import com.oceaneeda.server.domain.user.presentation.dto.request.UpdateUserInput;
import com.oceaneeda.server.domain.user.presentation.dto.response.UserResponse;
import com.oceaneeda.server.domain.user.service.CommandUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserMutationController {

    private final CommandUserService commandUserService;
    private final AuthReader authReader;

    @MutationMapping
    public UserResponse createUser(@Argument CreateUserInput input) {
        return UserResponse.from(commandUserService.createUser(input.toEntity(), input.password()));
    }

    @Authenticated
    @MutationMapping
    public UserResponse updateUser(@Argument UpdateUserInput input) {
        return UserResponse.from(commandUserService.updateUser(authReader.getCurrentUser(), input.toEntity()));
    }

    @Authenticated
    @MutationMapping
    public UserResponse deleteUser() {
        return UserResponse.from(commandUserService.deleteUser(authReader.getCurrentUser()));
    }
}
