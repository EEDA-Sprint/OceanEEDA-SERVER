package com.oceaneeda.server.domain.auth.exception;

import com.oceaneeda.server.global.exception.OceaneedaException;
import org.springframework.http.HttpStatus;

public class CredentialsInvalidException extends OceaneedaException {
    public CredentialsInvalidException() {
        super(HttpStatus.UNAUTHORIZED, "AUTH-401-4", "잘못된 이메일 또는 비밀번호입니다.");
    }
}