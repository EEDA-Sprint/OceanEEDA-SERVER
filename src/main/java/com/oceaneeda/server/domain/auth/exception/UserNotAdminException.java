package com.oceaneeda.server.domain.auth.exception;

import com.oceaneeda.server.global.exception.OceaneedaException;
import org.springframework.http.HttpStatus;

public class UserNotAdminException extends OceaneedaException {
    public UserNotAdminException() {
        super(HttpStatus.FORBIDDEN, "AUTH-403-1", "유저가 어드민이 아닙니다.");
    }
}
