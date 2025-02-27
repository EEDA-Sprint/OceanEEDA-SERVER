package com.oceaneeda.server.domain.auth.exception;

import com.oceaneeda.server.global.exception.OceaneedaException;
import org.springframework.http.HttpStatus;

public class TokenInvalidException extends OceaneedaException {
    public TokenInvalidException() {
        super(HttpStatus.UNAUTHORIZED, "AUTH-401-2", "토큰이 유효하지 않습니다.");
    }
}
