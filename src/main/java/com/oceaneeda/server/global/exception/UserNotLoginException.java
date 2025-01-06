package com.oceaneeda.server.global.exception;

public class UserNotLoginException extends RuntimeException {
    public UserNotLoginException(String message) {
        super(message);
    }
}
