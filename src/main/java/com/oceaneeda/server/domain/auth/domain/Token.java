package com.oceaneeda.server.domain.auth.domain;

public record Token(
        String accessToken,
        String refreshToken
) {
}