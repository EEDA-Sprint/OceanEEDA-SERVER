package com.oceaneeda.server.domain.user.presentation.dto.request;

import com.oceaneeda.server.domain.user.domain.User;

public record UpdateUserInput(
        String username,
        String email
) {
    public User toEntity() {
        return User.updateBuilder()
                .username(username)
                .email(email)
                .build();
    }
}