package com.oceaneeda.server.domain.auth.registry;

import com.oceaneeda.server.domain.auth.service.annotation.ForField;
import com.oceaneeda.server.domain.auth.service.core.FieldChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FieldCheckerRegistry {
    private final List<FieldChecker<?>> fieldCheckers;

    @SuppressWarnings("unchecked")
    public FieldChecker getChecker(Class inputType, String fieldName) {
        return fieldCheckers.stream()
                .filter(checker -> {
                    ForField annotation = checker.getClass().getAnnotation(ForField.class);
                    return annotation != null && annotation.inputType().equals(inputType)
                            && annotation.fieldName().equals(fieldName);
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No FieldPermissionChecker found for " + inputType.getSimpleName() + "." + fieldName
                ));
    }
}