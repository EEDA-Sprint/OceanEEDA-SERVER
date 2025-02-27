package com.oceaneeda.server.domain.file.exception;

import com.oceaneeda.server.global.exception.OceaneedaException;
import org.springframework.http.HttpStatus;

public class DirectoryCreationFailedException extends OceaneedaException {
    public DirectoryCreationFailedException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "FILE-500-1", "디렉토리 생성을 실패하였습니다.");
    }
}
