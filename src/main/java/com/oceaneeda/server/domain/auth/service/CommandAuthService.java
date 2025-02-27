package com.oceaneeda.server.domain.auth.service;

import com.oceaneeda.server.domain.auth.domain.Token;
import com.oceaneeda.server.domain.auth.service.command.LoginCommand;
import com.oceaneeda.server.domain.auth.service.implementation.AuthReader;
import com.oceaneeda.server.domain.auth.service.implementation.AuthValidator;
import com.oceaneeda.server.domain.auth.service.implementation.TokenProvider;
import com.oceaneeda.server.domain.auth.util.BearerTokenExtractor;
import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.service.implementation.UserReader;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommandAuthService {
    private final TokenProvider tokenProvider;
    private final AuthReader authReader;
    private final AuthValidator authValidator;
    private final UserReader userReader;

    public Token login(LoginCommand loginCommand) {
        User user = userReader.readByEmail(loginCommand.email());

        authValidator.validatePassword(loginCommand.password(), user);

        return tokenProvider.createNewTokens(user);
    }

    public Token refresh(String bearer) {
        String refreshToken = BearerTokenExtractor.extract(bearer);
        authValidator.shouldRefreshTokenValid(refreshToken);

        ObjectId userId = authReader.getIdFromJwt(refreshToken);
        String accessToken = tokenProvider.createAccessToken(userReader.read(userId));

        return new Token(accessToken, refreshToken);
    }
}