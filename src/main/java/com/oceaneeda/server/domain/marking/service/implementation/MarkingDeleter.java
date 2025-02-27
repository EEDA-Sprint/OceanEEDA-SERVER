package com.oceaneeda.server.domain.marking.service.implementation;

import com.oceaneeda.server.domain.marking.domain.Marking;
import com.oceaneeda.server.domain.marking.domain.repository.MarkingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarkingDeleter {
    private final MarkingRepository markingRepository;

    public void delete(Marking marking) {
        markingRepository.delete(marking);
    }
}
