package com.oceaneeda.server.domain.user.service;

import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.service.implementation.UserCreator;
import com.oceaneeda.server.domain.user.service.implementation.UserDeleter;
import com.oceaneeda.server.domain.user.service.implementation.UserUpdater;
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

    public User createUser(User user, String password) {
        userUpdater.updatePassword(user, password);
        return userCreator.create(user);
    }

    public User updateUser(User updatableUser, User user) {
        return userUpdater.update(updatableUser, user);
    }

    public User deleteUser(User deletableUser) {
        userDeleter.delete(deletableUser);
        return deletableUser;
    }
}
