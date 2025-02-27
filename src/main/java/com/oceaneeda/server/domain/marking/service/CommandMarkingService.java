package com.oceaneeda.server.domain.marking.service;

import com.oceaneeda.server.domain.file.service.implementation.FileDeleter;
import com.oceaneeda.server.domain.marking.domain.Marking;
import com.oceaneeda.server.domain.marking.service.implementation.MarkingCreator;
import com.oceaneeda.server.domain.marking.service.implementation.MarkingDeleter;
import com.oceaneeda.server.domain.marking.service.implementation.MarkingReader;
import com.oceaneeda.server.domain.marking.service.implementation.MarkingUpdater;
import com.oceaneeda.server.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommandMarkingService {

    private final MarkingCreator markingCreator;
    private final MarkingReader markingReader;
    private final MarkingUpdater markingUpdater;
    private final MarkingDeleter markingDeleter;
    private final FileDeleter fileDeleter;


    public Marking createMarking(Marking marking, User user) {
        return markingCreator.create(marking, user);
    }

    public Marking updateMarking(ObjectId markingId, Marking marking) {
        Marking updatableMarking = markingReader.read(markingId);
        return markingUpdater.update(updatableMarking, marking);
    }

    public Marking deleteMarking(ObjectId markingId) {
        Marking deletableMarking = markingReader.read(markingId);
        fileDeleter.deleteByMarking(deletableMarking.getFiles());
        markingDeleter.delete(deletableMarking);
        return deletableMarking;
    }
}
