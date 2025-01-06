package com.oceaneeda.server.domain.user.presentation;

import com.oceaneeda.server.domain.auth.annotation.Authenticated;
import com.oceaneeda.server.domain.auth.repository.AuthRepository;
import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.domain.repository.UserRepository;
import com.oceaneeda.server.domain.user.domain.value.Role;
import com.oceaneeda.server.domain.user.domain.value.Type;
import com.oceaneeda.server.domain.user.presentation.dto.request.CreateUserInput;
import com.oceaneeda.server.domain.user.presentation.dto.request.UpdateUserInput;
import com.oceaneeda.server.domain.user.presentation.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class UserMutationController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthRepository authRepository;

    // Mutation: 사용자 생성
    @MutationMapping
    public UserResponse createUser(@Argument("input") CreateUserInput input) {
        User newUser = new User();
        newUser.setId(new ObjectId());
        newUser.setUsername(input.username());
        newUser.setEmail(input.email());
        newUser.setPassword(passwordEncoder.encode(input.password()));
        newUser.setRole(Role.ROLE_USER);
        newUser.setSocialLoginType(Type.NONE);
        newUser.setCreatedAt(LocalDateTime.now());
        return UserResponse.from(userRepository.save(newUser));
    }

    // Mutation: 사용자 정보 수정
    @Authenticated
    @MutationMapping
    public UserResponse updateUser(@Argument UpdateUserInput input) {
        User existingUser = authRepository.getCurrentUser();

        if (input.username() != null) {
            existingUser.setUsername(input.username());
        }
        if (input.email() != null) {
            existingUser.setEmail(input.email());
        }
        if (input.password() != null) {
            existingUser.setPassword(passwordEncoder.encode(input.password()));
        }

        return UserResponse.from(userRepository.save(existingUser));
    }

    // Mutation: 사용자 삭제
    @Authenticated
    @MutationMapping
    public UserResponse deleteUser() {

        User deletedUser = authRepository.getCurrentUser();

        userRepository.delete(deletedUser);
        return UserResponse.from(deletedUser);
    }
}
