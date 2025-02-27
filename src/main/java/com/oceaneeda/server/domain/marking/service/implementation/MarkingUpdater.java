package com.oceaneeda.server.domain.marking.service.implementation;

import com.oceaneeda.server.domain.marking.domain.Marking;
import org.springframework.stereotype.Service;

@Service
public class MarkingUpdater {

    public Marking update(Marking updatableMarking, Marking marking) {
        updatableMarking.update(marking);
        return updatableMarking;
    }
}
