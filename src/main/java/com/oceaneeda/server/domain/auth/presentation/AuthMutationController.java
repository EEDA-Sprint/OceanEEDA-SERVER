package com.oceaneeda.server.domain.auth.presentation;


import com.oceaneeda.server.domain.auth.presentation.dto.request.LoginInput;
import com.oceaneeda.server.domain.auth.presentation.dto.request.RefreshInput;
import com.oceaneeda.server.domain.auth.presentation.dto.response.TokenResponse;
import com.oceaneeda.server.domain.auth.service.CommandAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthMutationController {

    private final CommandAuthService commandAuthService;

    @MutationMapping
    public TokenResponse login(@Argument LoginInput input) {
        return TokenResponse.from(commandAuthService.login(input.toCommand()));
    }

    @MutationMapping
    public TokenResponse refresh(@Argument RefreshInput input) {
        return TokenResponse.from(commandAuthService.refresh(input.refreshToken()));
    }

}