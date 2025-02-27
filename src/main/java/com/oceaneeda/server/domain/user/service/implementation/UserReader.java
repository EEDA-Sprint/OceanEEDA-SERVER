package com.oceaneeda.server.domain.user.service.implementation;

import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.domain.repository.UserRepository;
import com.oceaneeda.server.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserReader {
    private final UserRepository userRepository;

    public User readByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public User read(ObjectId userId) {
        return userRepository.getById(userId);
    }

    public List<User> readAll() {
        return userRepository.findAll();
    }
}
