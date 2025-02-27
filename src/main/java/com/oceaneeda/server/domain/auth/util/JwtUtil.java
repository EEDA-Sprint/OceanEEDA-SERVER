//package com.oceaneeda.server.domain.auth.util;
//
//import com.oceaneeda.server.domain.user.domain.value.Role;
//import com.oceaneeda.server.global.exception.ExpiredTokenException;
//import com.oceaneeda.server.global.exception.InvalidTokenException;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//
//    private final SecretKey secretKey;
////    private final RefreshRepository refreshRepository;
//    @Value("${spring.jwt.access.expiration}")
//    private long accessTokenExpiration;
//    @Value("${spring.jwt.refresh.expiration}")
//    private long refreshTokenExpiration;
//
//    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
//        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
//    }
//
//    public String getId(String token) {
//        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("sub", String.class);
//    }
//
//    public void shouldRefreshTokenValid(String token) {
//        try {
//            Jwts.parser()
//                    .verifyWith(secretKey)
//                    .build()
//                    .parse(token);
//        } catch (ExpiredJwtException e) {
//            throw new ExpiredTokenException("Expired token");
//        } catch (JwtException e) {
//            throw new InvalidTokenException("Invalid token");
//        }
//    }
//
//
////    public String getCategory(String token) {
////        try {
////            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
////        } catch (ExpiredJwtException e) {
////            return e.getClaims().get("category", String.class);
////        }
////    }
////
////    public Role getRole(String token) {
////        String roleValue = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
////        return Role.fromValue(roleValue);
////    }
////
////    public String getLoginType(String token) {
////        try {
////            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("loginType", String.class);
////        } catch (ExpiredJwtException e) {
////            return e.getClaims().get("loginType", String.class);
////        }
////    }
//
//    public String createAccessToken(String id, Role role, String loginType) {
//        return createJwt("access", id, role, loginType, accessTokenExpiration);
//    }
//
//    public String createRefreshToken(String id, Role role, String loginType) {
//        return createJwt("refresh", id, role, loginType, refreshTokenExpiration);
//    }
//
////    public void addRefreshToken(Long userId, String refreshToken) {
////
////        Date date = new Date(System.currentTimeMillis() + refreshTokenExpiration);
////
////        Refresh refresh = Refresh.builder()
////                .userId(userId)
////                .refreshToken(refreshToken)
////                .expiration(date.toString())
////                .build();
////
////        refreshRepository.save(refresh);
////    }
//
//    private String createJwt(String category, String id, Role role, String loginType, long expiredMs) {
//        return Jwts.builder()
//                .claim("category", category)
//                .claim("sub", String.valueOf(id))
//                .claim("role", role)
//                .claim("loginType", loginType)
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + expiredMs))
//                .signWith(secretKey)
//                .compact();
//    }
//}