package com.oceaneeda.server.domain.auth.service.command;

public record LoginCommand (
        String email,
        String password
) {
}
