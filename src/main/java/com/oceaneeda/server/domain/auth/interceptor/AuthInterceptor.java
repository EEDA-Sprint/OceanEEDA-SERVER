package com.oceaneeda.server.domain.auth.interceptor;

import com.oceaneeda.server.domain.auth.service.implementation.AuthUpdater;
import com.oceaneeda.server.domain.auth.util.BearerTokenExtractor;
import com.oceaneeda.server.domain.auth.util.JwtParser;
import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.service.implementation.UserReader;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final UserReader userReader;
    private final AuthUpdater authUpdater;
    private final JwtParser jwtParser;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {


        String bearer = request.getHeader(AUTHORIZATION);

        if (bearer == null) {
            authUpdater.updateCurrentUser(null);
        } else {
            String jwt = BearerTokenExtractor.extract(bearer);
            ObjectId userId = jwtParser.getIdFromJwt(jwt);

            User user = userReader.read(userId);

            authUpdater.updateCurrentUser(user);
        }
    return true;
    }

}
