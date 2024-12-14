package com.oceaneeda.server.domain.user.presentation;

import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.domain.value.Role;
import com.oceaneeda.server.domain.user.domain.value.Type;
import com.oceaneeda.server.domain.user.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserGraphQLController {
    private final UserService userService;

    public UserGraphQLController(UserService userService) {
        this.userService = userService;
    }

    @QueryMapping
    public User getUserById(@Argument String id) {
        return userService.getUserById(id);
    }

    @QueryMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @MutationMapping
    public User createUser(@Argument String username, @Argument String email, @Argument String password,
                           @Argument String socialLoginType, @Argument String role) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setSocialLoginType(Type.valueOf(socialLoginType));
        user.setRole(Role.valueOf(role));
        return userService.createUser(user);
    }

    @MutationMapping
    public User updateUser(@Argument String id, @Argument String username, @Argument String email,
                           @Argument String password, @Argument String socialLoginType, @Argument String role,
                           @Argument String imagePath) {
        User updatedUser = new User();
        updatedUser.setUsername(username);
        updatedUser.setEmail(email);
        updatedUser.setPassword(password);
        if (socialLoginType != null) {
            updatedUser.setSocialLoginType(Type.valueOf(socialLoginType));
        }
        if (role != null) {
            updatedUser.setRole(Role.valueOf(role));
        }
        updatedUser.setImagePath(imagePath);
        return userService.updateUser(id, updatedUser);
    }

    @MutationMapping
    public boolean deleteUser(@Argument String id) {
        userService.deleteUser(new ObjectId(id));
        return true;
    }
}