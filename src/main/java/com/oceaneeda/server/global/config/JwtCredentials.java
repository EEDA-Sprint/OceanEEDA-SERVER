package com.oceaneeda.server.global.config;

import io.jsonwebtoken.Jwts;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static java.nio.charset.StandardCharsets.*;

@ConfigurationProperties(prefix = "jwt")
public record JwtCredentials(
        SecretKey secretKey,
        long accessTokenExpirationTime,
        long refreshTokenExpirationTime
) {

    @ConstructorBinding
    public JwtCredentials(String secretKey, long accessTokenExpirationTime, long refreshTokenExpirationTime) {
        this(
                new SecretKeySpec(secretKey.getBytes(UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm()),
                accessTokenExpirationTime,
                refreshTokenExpirationTime
        );
    }
}