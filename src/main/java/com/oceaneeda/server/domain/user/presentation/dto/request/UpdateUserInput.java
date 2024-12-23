package com.oceaneeda.server.domain.user.presentation.dto.request;

public record UpdateUserInput(
        String username,
        String email,
        String password
) {}