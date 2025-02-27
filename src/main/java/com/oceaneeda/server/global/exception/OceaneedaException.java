package com.oceaneeda.server.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class OceaneedaException extends RuntimeException {
    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}