package com.oceaneeda.server.domain.auth.util;

import org.aspectj.lang.JoinPoint;
import org.bson.types.ObjectId;

import java.util.Arrays;

public class JoinPointArgumentExtractor {
    public static Object findArgumentByType(JoinPoint joinPoint, Class<?> targetType) {
        return Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg != null && targetType.isAssignableFrom(arg.getClass()))
                .findFirst()
                .orElse(null);
    }

    public static ObjectId findArgumentByObjectId(JoinPoint joinPoint) {
        Object result = findArgumentByType(joinPoint, ObjectId.class);
        return (ObjectId) result;
    }
}
