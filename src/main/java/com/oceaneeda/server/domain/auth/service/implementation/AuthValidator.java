package com.oceaneeda.server.domain.auth.service.implementation;

import com.oceaneeda.server.domain.auth.exception.CredentialsInvalidException;
import com.oceaneeda.server.domain.auth.exception.TokenExpiredException;
import com.oceaneeda.server.domain.auth.exception.TokenInvalidException;
import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.global.config.JwtCredentials;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthValidator {
    private final JwtCredentials jwtCredentials;
    private final PasswordEncoder passwordEncoder;

    public void validatePassword(String password, User user) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CredentialsInvalidException();
        }
    }

    public void shouldRefreshTokenValid(String refreshToken) {
        try {
            Jwts.parser()
                    .verifyWith(jwtCredentials.secretKey())
                    .build()
                    .parse(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException();
        } catch (JwtException e) {
            throw new TokenInvalidException();
        }
    }
}