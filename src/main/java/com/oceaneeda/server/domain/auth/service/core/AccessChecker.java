package com.oceaneeda.server.domain.auth.service.core;

import com.oceaneeda.server.domain.user.domain.User;

public interface AccessChecker<T> {
    void checkAccess(T resourceId, User user);
}
