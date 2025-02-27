package com.oceaneeda.server.domain.marking.exception;

import com.oceaneeda.server.global.exception.OceaneedaException;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;

public class MarkingNotFoundException extends OceaneedaException {
    public MarkingNotFoundException(ObjectId markingId) {
        super(HttpStatus.NOT_FOUND, "MARKING-404-1", markingId + "이 아이디인 마커가 존재하지 않습니다.");
    }
}
