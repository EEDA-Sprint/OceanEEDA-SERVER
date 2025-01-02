package com.oceaneeda.server.domain.auth.presentation.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
