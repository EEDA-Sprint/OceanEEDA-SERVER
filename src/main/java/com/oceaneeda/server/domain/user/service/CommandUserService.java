package com.oceaneeda.server.domain.user.service;

import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.service.implementation.UserCreator;
import com.oceaneeda.server.domain.user.service.implementation.UserDeleter;
import com.oceaneeda.server.domain.user.service.implementation.UserUpdater;
import com.oceaneeda.server.domain.user.service.implementation.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommandUserService {

    private final UserCreator userCreator;
    private final UserUpdater userUpdater;
    private final UserDeleter userDeleter;
    private final UserValidator userValidator;

    public User createUser(User user, String password) {
        userValidator.shouldNotExistEmail(user);
        userUpdater.updatePassword(user, password);
        return userCreator.create(user);
    }

    public User updateUser(User updatableUser, User user) {
        userValidator.shouldNotExistEmail(user);
        return userUpdater.update(updatableUser, user);
    }

    public User deleteUser(User deletableUser) {
        userDeleter.delete(deletableUser);
        return deletableUser;
    }
}
