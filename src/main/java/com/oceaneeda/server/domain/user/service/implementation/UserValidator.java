package com.oceaneeda.server.domain.user.service.implementation;

import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.domain.repository.UserRepository;
import com.oceaneeda.server.domain.user.exception.UserEmailAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;

    public void shouldNotExistEmail(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserEmailAlreadyExistException(user.getEmail());
        }
    }
}
