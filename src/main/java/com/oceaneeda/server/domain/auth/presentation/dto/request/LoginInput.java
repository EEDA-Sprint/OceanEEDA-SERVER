package com.oceaneeda.server.domain.auth.presentation.dto.request;

public record LoginInput(
        String email,
        String password
) {
}
