package com.oceaneeda.server.domain.user.presentation.dto.request;

public record CreateUserInput(
        String username,
        String email,
        String password
) {}