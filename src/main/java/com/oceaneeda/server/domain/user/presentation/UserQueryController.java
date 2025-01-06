package com.oceaneeda.server.domain.user.presentation;

import com.oceaneeda.server.domain.auth.annotation.Authenticated;
import com.oceaneeda.server.domain.auth.repository.AuthRepository;
import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.domain.repository.UserRepository;
import com.oceaneeda.server.domain.user.presentation.dto.response.UserResponse;
import com.oceaneeda.server.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserQueryController {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;

    // Query: 특정 사용자 조회
    @QueryMapping
    public UserResponse getUserById(@Argument ObjectId id) {
        return userRepository.findById(id)
                .map(UserResponse::from)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id: " + id));
    }

    // Query: 전체 사용자 조회
    @QueryMapping
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::from)
                .toList();
    }

    @Authenticated
    @QueryMapping
    public UserResponse getUserByCurrent() {
        return UserResponse.from(authRepository.getCurrentUser());
    }
}
