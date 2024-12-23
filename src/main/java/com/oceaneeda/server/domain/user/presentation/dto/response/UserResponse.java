package com.oceaneeda.server.domain.user.presentation.dto.response;

import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.domain.value.Type;
import lombok.Builder;
import org.bson.types.ObjectId;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Builder
public record UserResponse(
        ObjectId id,
        String username,
        String email,
        RoleResponse role,
        Type socialLoginType,
        OffsetDateTime createdAt
) {
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(RoleResponse.valueOf(user.getRole().name().split("_")[1]))
                .socialLoginType(user.getSocialLoginType())
                .createdAt(user.getCreatedAt().atOffset(ZoneOffset.UTC))
                .build();
    }
}
