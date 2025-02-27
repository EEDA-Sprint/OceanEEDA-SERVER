package com.oceaneeda.server.domain.marking.exception;

import com.oceaneeda.server.global.exception.OceaneedaException;
import org.springframework.http.HttpStatus;

public class NotMatchMarkingWriterException extends OceaneedaException {
    public NotMatchMarkingWriterException() {
        super(HttpStatus.FORBIDDEN, "MARKING-403-1", "마킹 생성자가 아닙니다.");
    }
}
