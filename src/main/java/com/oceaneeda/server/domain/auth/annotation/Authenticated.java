package com.oceaneeda.server.domain.auth.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@CustomChecker
public @interface Authenticated {
}
