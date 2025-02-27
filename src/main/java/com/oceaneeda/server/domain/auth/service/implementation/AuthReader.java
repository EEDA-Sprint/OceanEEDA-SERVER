package com.oceaneeda.server.domain.auth.service.implementation;

import com.oceaneeda.server.domain.auth.repository.AuthRepository;
import com.oceaneeda.server.domain.auth.util.JwtParser;
import com.oceaneeda.server.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthReader {
    private final AuthRepository authRepository;
    private final JwtParser jwtParser;

    public ObjectId getIdFromJwt(String token) {
        return jwtParser.getIdFromJwt(token);
    }

    public User getCurrentUser() {
        return authRepository.getCurrentUser();
    }

    public User getNullableCurrentUser() {
        return authRepository.getNullableCurrentUser();
    }
}