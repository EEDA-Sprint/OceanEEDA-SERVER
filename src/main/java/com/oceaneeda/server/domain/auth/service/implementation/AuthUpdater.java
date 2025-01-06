package com.oceaneeda.server.domain.auth.service.implementation;

import com.oceaneeda.server.domain.auth.repository.AuthRepository;
import com.oceaneeda.server.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUpdater {
    private final AuthRepository authRepository;

    public void updateCurrentUser(User user) {
        authRepository.updateCurrentUser(user);
    }
}