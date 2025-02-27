package com.oceaneeda.server.domain.auth.repository;

import com.oceaneeda.server.domain.auth.exception.UserNotLoginException;
import com.oceaneeda.server.domain.user.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;

@Repository
@RequestScope
public class AuthRepository {
    private User currentUser;

    public User getCurrentUser() {
        if (currentUser == null) {
            throw new UserNotLoginException();
        }
        return currentUser;
    }

    public User getNullableCurrentUser() {
        return currentUser;
    }

    public void updateCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
