package com.oceaneeda.server.domain.auth.exception;

import com.oceaneeda.server.global.exception.OceaneedaException;
import org.springframework.http.HttpStatus;

public class UserNotLoginException extends OceaneedaException {
    public UserNotLoginException() {
        super(HttpStatus.UNAUTHORIZED, "AUTH-401-5", "유저가 어드민이 아닙니다.");
    }
}
