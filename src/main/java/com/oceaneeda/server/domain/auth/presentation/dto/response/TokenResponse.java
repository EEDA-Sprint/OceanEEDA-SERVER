package com.oceaneeda.server.domain.auth.presentation.dto.response;

import com.oceaneeda.server.domain.auth.domain.Token;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
    public static TokenResponse from(Token token) {
        return new TokenResponse(
                token.accessToken(),
                token.refreshToken()
        );
    }
}
