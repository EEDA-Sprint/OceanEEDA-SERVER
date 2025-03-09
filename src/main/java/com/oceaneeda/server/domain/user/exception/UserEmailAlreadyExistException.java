package com.oceaneeda.server.domain.user.exception;

import com.oceaneeda.server.global.exception.OceaneedaException;
import org.springframework.http.HttpStatus;

public class UserEmailAlreadyExistException extends OceaneedaException {
    public UserEmailAlreadyExistException(String email) {
        super(HttpStatus.CONFLICT, "USER-409-1", email + "이 이메일인 유저가 이미 존재합니다.");
    }
}
