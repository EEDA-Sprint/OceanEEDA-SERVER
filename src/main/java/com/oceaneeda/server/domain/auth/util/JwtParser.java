package com.oceaneeda.server.domain.auth.util;

import com.oceaneeda.server.domain.auth.exception.TokenExpiredException;
import com.oceaneeda.server.domain.auth.exception.TokenInvalidException;
import com.oceaneeda.server.global.config.JwtCredentials;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtParser {
    public static final String ID = "sub";
    private final JwtCredentials jwtCredentials;

    public ObjectId getIdFromJwt(String jwt) {
        try {
            return new ObjectId(Jwts.parser()
                    .verifyWith(jwtCredentials.secretKey())
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload()
                    .get(ID, String.class));
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException();
        } catch (JwtException e) {
            System.out.println("JWT Exception : " + e.getMessage());
            throw new TokenInvalidException();
        }
    }
}
