package com.oceaneeda.server.domain.user.service;

import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.service.implementation.UserReader;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QueryUserService {

    private final UserReader userReader;

    public User readOne(ObjectId id) {
        return userReader.read(id);
    }

    public List<User> readAll() {
        return userReader.readAll();
    }
}
