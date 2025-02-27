package com.oceaneeda.server.domain.marking.service;

import com.oceaneeda.server.domain.marking.domain.Marking;
import com.oceaneeda.server.domain.marking.service.implementation.MarkingReader;
import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.domain.value.Role;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QueryMarkingService {

    private final MarkingReader markingReader;

    public Marking readOne(ObjectId markingId) {
        return markingReader.read(markingId);
    }

    public List<Marking> readAll(User user) {
        List<Marking> markings = markingReader.findPublic();

        if (user == null) {
            return markings;
        }
        if (user.getRole() == Role.ADMIN) {
            return markingReader.readAll();
        }
        markings.addAll(markingReader.findPrivate(user));

        return markings;
    }
}
