package com.oceaneeda.server.domain.user.service.implementation;

import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDeleter {
    private final UserRepository userRepository;

    public void delete(User user) {
        userRepository.delete(user);
    }
}
