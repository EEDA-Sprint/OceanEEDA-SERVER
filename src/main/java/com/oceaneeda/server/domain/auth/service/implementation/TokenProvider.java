package com.oceaneeda.server.domain.auth.service.implementation;

import com.oceaneeda.server.domain.auth.domain.Token;
import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.global.config.JwtCredentials;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenProvider {
    private final JwtCredentials jwtCredentials;

    public Token createNewTokens(User user) {
        return new Token(
                createAccessToken(user),
                createRefreshToken(user)
        );
    }

    public String createAccessToken(User user) {
        return createToken(user, jwtCredentials.accessTokenExpirationTime());
    }

    private String createRefreshToken(User user) {
        return createToken(user, jwtCredentials.refreshTokenExpirationTime());
    }

    private String createToken(User user, long expireLength) {
        Date expiration = new Date(System.currentTimeMillis() + expireLength);
        return Jwts.builder()
                .claim("sub", user.getId())
                .expiration(expiration)
                .signWith(jwtCredentials.secretKey())
                .compact();
    }
}
