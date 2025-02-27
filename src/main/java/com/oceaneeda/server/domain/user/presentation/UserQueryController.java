package com.oceaneeda.server.domain.user.presentation;

import com.oceaneeda.server.domain.auth.annotation.Authenticated;
import com.oceaneeda.server.domain.auth.service.implementation.AuthReader;
import com.oceaneeda.server.domain.user.presentation.dto.response.UserResponse;
import com.oceaneeda.server.domain.user.service.QueryUserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserQueryController {

    private final QueryUserService queryUserService;
    private final AuthReader authReader;

    @QueryMapping
    public UserResponse getUserById(@Argument ObjectId id) {
        return UserResponse.from(queryUserService.readOne(id));
    }

    @QueryMapping
    public List<UserResponse> getAllUsers() {
        return queryUserService.readAll().stream()
                .map(UserResponse::from)
                .toList();
    }

    @Authenticated
    @QueryMapping
    public UserResponse getUserByCurrent() {
        return UserResponse.from(authReader.getCurrentUser());
    }
}
