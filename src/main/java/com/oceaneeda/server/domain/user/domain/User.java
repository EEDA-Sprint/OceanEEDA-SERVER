package com.oceaneeda.server.domain.user.domain;

import com.oceaneeda.server.domain.user.domain.value.Role;
import com.oceaneeda.server.domain.user.domain.value.Type;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;


@Document(collection = "users")
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    private ObjectId id;

    private String username;

    @Indexed(unique = true)
    private String email;

    private String password;

    private Type socialLoginType;

    private Role role;

    private String imagePath;

    @CreatedDate
    private LocalDateTime createdAt;

}