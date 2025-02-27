package com.oceaneeda.server.domain.user.presentation.dto.request;

import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.domain.value.Role;
import com.oceaneeda.server.domain.user.domain.value.Type;

public record CreateUserInput(
        String username,
        String email,
        String password
) {
    public User toEntity() {
        return User.builder()
                .username(username)
                .email(email)
                .socialLoginType(Type.NONE)
                .role(Role.USER)
                .build();
    }
}