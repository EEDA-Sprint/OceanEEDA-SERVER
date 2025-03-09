package com.oceaneeda.server.global.dirtychecking;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class MongoPersistenceContext {
    private final ObjectMapper objectMapper;
    private final Map<Object, Object> entitySnapshot = new HashMap<>();

    @Autowired
    public MongoPersistenceContext(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void persist(Object entity) {
        if (entity == null) {
            return;
        }

        // Optional 타입이면 내부 값을 가져와서 처리
        if (entity instanceof Optional<?>) {
            ((Optional<?>) entity).ifPresent(this::persist);
            return;
        }

        // 스프링 데이터 프록시 엔티티 등은 처리하지 않기
        if (entity instanceof Proxy || entity instanceof Advised) {
            log.debug("프록시 객체는 스냅샷에서 제외됨: {}", entity.getClass().getName());
            return;
        }

        try {
            entitySnapshot.put(entity, cloneEntity(entity));
        } catch (Exception e) {
            log.warn("엔티티 복제 중 오류 발생, 이 엔티티는 더티 체킹에서 제외됨: {}, 오류: {}",
                    entity.getClass().getName(), e.getMessage());
        }
    }

    public boolean isDirty(Object entity) {
        Object original = entitySnapshot.get(entity);
        if (original == null) {
            return false;
        }

        try {
            String originalJson = objectMapper.writeValueAsString(original);
            String currentJson = objectMapper.writeValueAsString(entity);

            return !originalJson.equals(currentJson);
        } catch (Exception e) {
            log.warn("엔티티 비교 중 오류 발생: {}", e.getMessage());
            return false; // 비교할 수 없는 경우 변경되지 않은 것으로 간주
        }
    }

    public void clear() {
        entitySnapshot.clear();
    }

    private Object cloneEntity(Object entity) {
        try {
            String json = objectMapper.writeValueAsString(entity);
            return objectMapper.readValue(json, entity.getClass());
        } catch (Exception e) {
            throw new RuntimeException("Failed to clone entity: " + e.getMessage(), e);
        }
    }

    public Iterable<Object> getAllEntities() {
        return entitySnapshot.keySet();
    }
}