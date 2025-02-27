package com.oceaneeda.server.domain.auth.exception;

import com.oceaneeda.server.global.exception.OceaneedaException;
import org.springframework.http.HttpStatus;

public class TokenMissingException extends OceaneedaException {
    public TokenMissingException() {
        super(HttpStatus.UNAUTHORIZED, "AUTH-401-3", "토큰이 존재하지 않습니다.");
    }
}