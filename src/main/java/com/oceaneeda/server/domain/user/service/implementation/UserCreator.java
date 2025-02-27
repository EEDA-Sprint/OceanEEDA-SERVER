package com.oceaneeda.server.domain.user.service.implementation;

import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCreator {
    private final UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }
}
