package com.oceaneeda.server.domain.auth.service.core;

import com.oceaneeda.server.domain.user.domain.User;

public interface FieldChecker<T> {
    void checkField(T input, User currentUser);
}