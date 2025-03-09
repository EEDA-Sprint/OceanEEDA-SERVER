package com.oceaneeda.server.domain.auth.service.annotation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;

@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ForField {
    Class<?> inputType();
    String fieldName();
}