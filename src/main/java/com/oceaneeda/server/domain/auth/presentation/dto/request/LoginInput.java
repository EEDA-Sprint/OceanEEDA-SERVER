package com.oceaneeda.server.domain.auth.presentation.dto.request;

import com.oceaneeda.server.domain.auth.service.command.LoginCommand;

public record LoginInput(
        String email,
        String password
) {
    public LoginCommand toCommand() {
        return new LoginCommand(email, password);
    }
}
