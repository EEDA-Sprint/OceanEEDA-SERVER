package com.oceaneeda.server.global.exception;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class ExceptionScanner {

    public static Set<Class<? extends OceaneedaException>> scanExceptions(String packageName) {
        Reflections reflections = new Reflections(packageName, Scanners.SubTypes);

        return reflections.getSubTypesOf(OceaneedaException.class).stream()
                // 추상 클래스 제외, 실제 예외 클래스만 반환
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                // 베이스 예외 클래스 자체는 제외
                .filter(clazz -> !clazz.equals(OceaneedaException.class))
                .collect(Collectors.toSet());
    }
}