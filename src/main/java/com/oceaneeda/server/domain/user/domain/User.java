package com.oceaneeda.server.domain.user.domain;

import com.oceaneeda.server.domain.user.domain.value.Role;
import com.oceaneeda.server.domain.user.domain.value.Type;
import com.oceaneeda.server.global.constants.MongoCollections;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;


@Document(collection = MongoCollections.USER)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    private ObjectId id;

    private String username;

    @Indexed(unique = true)
    private String email;

    private String password;

    private Type socialLoginType;

    private Role role;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public User(String username,
                String email,
                Type socialLoginType,
                Role role) {
        this.username = username;
        this.email = email;
        this.socialLoginType = socialLoginType;
        this.role = role;
    }

    @Builder(builderMethodName = "updateBuilder")
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public void update(User user) {
        if (user.username != null ) this.username = user.username;
        if (user.email != null ) this.email = user.email;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}