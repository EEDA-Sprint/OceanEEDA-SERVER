package com.oceaneeda.server.domain.file.exception;

import com.oceaneeda.server.global.exception.OceaneedaException;
import org.springframework.http.HttpStatus;

public class FileDeleteFailedException extends OceaneedaException {
    public FileDeleteFailedException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "FILE-500-3", "파일 삭제를 실패하였습니다.");
    }
}
