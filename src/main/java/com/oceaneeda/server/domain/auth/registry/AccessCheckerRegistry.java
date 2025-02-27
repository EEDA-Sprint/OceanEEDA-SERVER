package com.oceaneeda.server.domain.auth.registry;

import com.oceaneeda.server.domain.auth.service.annotation.ForEntity;
import com.oceaneeda.server.domain.auth.service.core.AccessChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccessCheckerRegistry {
    private final List<AccessChecker<?>> accessCheckers;

    @SuppressWarnings("unchecked")
    public  AccessChecker getChecker(Class type) {
        return accessCheckers.stream()
                .filter(checker -> {
                    ForEntity forEntityAnnotation = checker.getClass().getAnnotation(ForEntity.class);
                    return forEntityAnnotation != null && forEntityAnnotation.value().equals(type);
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No AccessChecker found for " + type.getSimpleName()));
    }
}
