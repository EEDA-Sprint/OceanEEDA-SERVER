package com.oceaneeda.server.global.dirtychecking;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.mongojack.internal.MongoJackModule;
import org.springframework.aop.framework.Advised;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class MongoPersistenceContext {
    private final Map<Object, Object> entitySnapshot = new HashMap<>();

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

        entitySnapshot.put(entity, cloneEntity(entity));
    }

    public boolean isDirty(Object entity) {
        Object original = entitySnapshot.get(entity);
        if (original == null) {
            return false;
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            String originalJson = objectMapper.writeValueAsString(original);
            String currentJson = objectMapper.writeValueAsString(entity);

            return !originalJson.equals(currentJson);
        } catch (Exception e) {
            throw new RuntimeException("Failed to compare entities: " + e.getMessage(), e);
        }
    }

    public void clear() {
        entitySnapshot.clear();
    }

    private Object cloneEntity(Object entity) {
        // null 체크
        if (entity == null) {
            return null;
        }

        // Optional 타입 체크
        if (entity instanceof Optional) {
            return entity; // Optional은 복제하지 않고 그대로 반환
        }

        // 기본 불변 타입 체크
        if (entity instanceof String || entity instanceof Number || entity instanceof Boolean ||
                entity instanceof Enum || entity.getClass().isPrimitive()) {
            return entity; // 불변 객체는 그대로 반환
        }

        try {
            // 새 인스턴스 생성
            Object clone = entity.getClass().getDeclaredConstructor().newInstance();

            // 모든 필드를 순회하며 복사
            ReflectionUtils.doWithFields(entity.getClass(), field -> {
                try {
                    field.setAccessible(true);
                    Object value = field.get(entity);

                    // static이나 final 필드는 건너뛰기
                    if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
                        return;
                    }

                    field.set(clone, value);
                } catch (IllegalAccessException e) {
                    // 접근할 수 없는 필드는 무시
                    log.debug("필드 복사 중 접근 오류: {}", field.getName());
                }
            });

            return clone;
        } catch (Exception e) {
            throw new RuntimeException("Failed to clone entity: " + e.getMessage(), e);
        }
    }

    public Iterable<Object> getAllEntities() {
        return entitySnapshot.keySet();
    }
}
