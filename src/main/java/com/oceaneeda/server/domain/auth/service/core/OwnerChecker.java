package com.oceaneeda.server.domain.auth.service.core;

import com.oceaneeda.server.domain.user.domain.User;

public interface OwnerChecker<T> {
    void checkOwner(T resourceId, User user);
}