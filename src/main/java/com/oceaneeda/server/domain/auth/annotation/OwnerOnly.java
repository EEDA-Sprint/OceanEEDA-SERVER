package com.oceaneeda.server.domain.auth.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OwnerOnly {
    Class<?> resourceType();
}