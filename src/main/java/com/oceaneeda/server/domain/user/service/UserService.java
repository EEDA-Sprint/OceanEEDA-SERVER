package com.oceaneeda.server.domain.user.service;

import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.domain.repository.UserRepository;
import com.oceaneeda.server.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(String id) {
        if (!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("The provided ID is not a valid ObjectId.");
        }
        return userRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new EntityNotFoundException("User not found for ID: " + id));
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String id, User updatedUser) {
        User user = getUserById(id);
        log.warn(updatedUser.toString());
        if (updatedUser.getUsername() != null) user.setUsername(updatedUser.getUsername());
        if (updatedUser.getEmail() != null) user.setEmail(updatedUser.getEmail());
        if (updatedUser.getPassword() != null) user.setPassword(updatedUser.getPassword());
        if (updatedUser.getSocialLoginType() != null) user.setSocialLoginType(updatedUser.getSocialLoginType());
        if (updatedUser.getRole() != null) user.setRole(updatedUser.getRole());
        if (updatedUser.getImagePath() != null) user.setImagePath(updatedUser.getImagePath());
        return userRepository.save(user);
    }

    public void deleteUser(ObjectId id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }
}