package com.oceaneeda.server.domain.file.exception;

import com.oceaneeda.server.global.exception.OceaneedaException;
import org.springframework.http.HttpStatus;

public class FileUploadFailedException extends OceaneedaException {
    public FileUploadFailedException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "FILE-500-2", "파일 저장을 실패하였습니다.");
    }
}
