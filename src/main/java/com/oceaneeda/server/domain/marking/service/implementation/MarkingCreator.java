package com.oceaneeda.server.domain.marking.service.implementation;

import com.oceaneeda.server.domain.marking.domain.Marking;
import com.oceaneeda.server.domain.marking.domain.repository.MarkingRepository;
import com.oceaneeda.server.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarkingCreator {
    private final MarkingRepository markingRepository;

    public Marking create(Marking marking, User user) {
        marking.updateUser(user);
        return markingRepository.save(marking);
    }
}
