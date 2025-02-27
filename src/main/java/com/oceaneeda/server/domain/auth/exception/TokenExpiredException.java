package com.oceaneeda.server.domain.auth.exception;

import com.oceaneeda.server.global.exception.OceaneedaException;
import org.springframework.http.HttpStatus;

public class TokenExpiredException extends OceaneedaException {
    public TokenExpiredException() {
        super(HttpStatus.UNAUTHORIZED, "AUTH-401-1", "토큰이 만료되었습니다.");
    }
}
