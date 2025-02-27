package com.oceaneeda.server.domain.marking.service.implementation;

import com.oceaneeda.server.domain.marking.domain.Marking;
import com.oceaneeda.server.domain.marking.domain.repository.MarkingRepository;
import com.oceaneeda.server.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkingReader {
    private final MarkingRepository markingRepository;

    public Marking read(ObjectId markingId) {
        return markingRepository.getById(markingId);
    }

    public List<Marking> readAll() {
        return markingRepository.findAll();
    }

    public List<Marking> findPublic() {
        return markingRepository.findByIsApprovedTrue();
    }

    public List<Marking> findPrivate(User user) {
        return markingRepository.findByUserId(user.getId());
    }
}
