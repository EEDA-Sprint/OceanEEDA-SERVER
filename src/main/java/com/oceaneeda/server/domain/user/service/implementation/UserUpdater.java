package com.oceaneeda.server.domain.user.service.implementation;

import com.oceaneeda.server.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUpdater {
    private final PasswordEncoder passwordEncoder;

    public void updatePassword(User user, String password) {
        user.updatePassword(passwordEncoder.encode(password));
    }

    public User update(User updatableUser, User user) {
        updatableUser.update(user);
        return updatableUser;
    }
}
