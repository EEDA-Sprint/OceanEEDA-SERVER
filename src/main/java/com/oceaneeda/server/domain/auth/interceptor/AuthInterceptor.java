package com.oceaneeda.server.domain.auth.interceptor;

import com.oceaneeda.server.domain.auth.service.implementation.AuthUpdater;
import com.oceaneeda.server.domain.auth.util.JwtUtil;
import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.domain.repository.UserRepository;
import com.oceaneeda.server.global.exception.EntityNotFoundException;
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

    private final JwtUtil jwtUtil;
    private final AuthUpdater authUpdater;
    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String bearer = request.getHeader(AUTHORIZATION);
        log.warn("Bearer token : {}", bearer);

        if (bearer == null) {
            authUpdater.updateCurrentUser(null);
        } else {
            String userId = jwtUtil.getId(bearer.replace("Bearer", "").trim());
            User user = userRepository.findById(new ObjectId(userId))
                    .orElseThrow(() -> new EntityNotFoundException("Invalid userId"));

            authUpdater.updateCurrentUser(user);
        }
    return true;
    }

}
