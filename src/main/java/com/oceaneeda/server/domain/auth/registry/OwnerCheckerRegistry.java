package com.oceaneeda.server.domain.auth.registry;

import com.oceaneeda.server.domain.auth.service.annotation.ForEntity;
import com.oceaneeda.server.domain.auth.service.core.OwnerChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OwnerCheckerRegistry {
    private final List<OwnerChecker<?>> ownerCheckers;

    @SuppressWarnings("unchecked")
    public  OwnerChecker getChecker(Class type) {
        return ownerCheckers.stream()
                .filter(checker -> {
                    ForEntity forEntityAnnotation = checker.getClass().getAnnotation(ForEntity.class);
                    return forEntityAnnotation != null && forEntityAnnotation.value().equals(type);
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No OwnerChecker found for " + type.getSimpleName()));
    }
}