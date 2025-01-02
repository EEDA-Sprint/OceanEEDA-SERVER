package com.oceaneeda.server.domain.auth.presentation;


import com.oceaneeda.server.domain.auth.presentation.dto.request.LoginInput;
import com.oceaneeda.server.domain.auth.presentation.dto.response.TokenResponse;
import com.oceaneeda.server.domain.auth.util.JwtUtil;
import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.domain.repository.UserRepository;
import com.oceaneeda.server.global.exception.EntityNotFoundException;
import com.oceaneeda.server.global.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthMutationController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    @MutationMapping
    public TokenResponse login(@Argument LoginInput input) {
        User user = userRepository.findByEmail(input.email())
                .orElseThrow(() -> new EntityNotFoundException("Invalid username or password"));

        if (!passwordEncoder.matches(input.password(), user.getPassword())) {
            throw new UnauthorizedException("Invalid username or password");
        }

        String accessToken = jwtUtil.createAccessToken(user.getId().toHexString(), user.getRole(), user.getSocialLoginType().name());
        String refreshToken = jwtUtil.createRefreshToken(user.getId().toHexString(), user.getRole(), user.getSocialLoginType().name());

        return new TokenResponse(accessToken, refreshToken);
    }


}
