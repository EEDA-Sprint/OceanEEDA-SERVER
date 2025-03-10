package com.oceaneeda.server.global.exception;

import lombok.Getter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

public class StandardExceptionMapping {

    @Getter
    private static final Map<Class<? extends Exception>, ExceptionMappingInfo> mappings = new HashMap<>();

    static {
        mappings.put(IllegalArgumentException.class, new ExceptionMappingInfo(
                "VALIDATION",
                "INVALID_INPUT",
                HttpStatus.BAD_REQUEST,
                "입력 값이 올바르지 않습니다"
        ));

        mappings.put(MethodArgumentTypeMismatchException.class, new ExceptionMappingInfo(
                "VALIDATION",
                "INVALID_INPUT",
                HttpStatus.BAD_REQUEST,
                "입력 값이 올바르지 않습니다"
        ));

        mappings.put(BindException.class, new ExceptionMappingInfo(
                "VALIDATION",
                "INVALID_INPUT",
                HttpStatus.BAD_REQUEST,
                "입력 값이 올바르지 않습니다"
        ));

        mappings.put(DuplicateKeyException.class, new ExceptionMappingInfo(
                "DATABASE",
                "DUPLICATE_VALUE",
                HttpStatus.CONFLICT,
                "MongoDB 유니크 제약이 위반되었습니다."
        ));

    }

        public record ExceptionMappingInfo(
                String category,
                String errorCode,
                HttpStatus status,
                String message
        ) {

    }
}