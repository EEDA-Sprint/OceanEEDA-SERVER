package com.oceaneeda.server.domain.auth.interceptor;

import com.oceaneeda.server.domain.auth.annotation.AdminOnly;
import com.oceaneeda.server.domain.auth.annotation.Authenticated;
import com.oceaneeda.server.domain.auth.annotation.CustomChecker;
import com.oceaneeda.server.domain.auth.annotation.LoginOrNot;
import com.oceaneeda.server.domain.auth.handler.GraphqlHandlerMapping;
import com.oceaneeda.server.domain.auth.handler.GraphqlHandlerMethod;
import com.oceaneeda.server.domain.auth.service.implementation.AuthUpdater;
import com.oceaneeda.server.domain.auth.util.BearerTokenExtractor;
import com.oceaneeda.server.domain.auth.util.JwtParser;
import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.domain.user.service.implementation.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import graphql.language.Document;
import graphql.language.Field;
import graphql.language.OperationDefinition;
import graphql.parser.Parser;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class GraphqlInterceptor implements WebGraphQlInterceptor {

    private final UserReader userReader;
    private final AuthUpdater authUpdater;
    private final JwtParser jwtParser;
    private final GraphqlHandlerMapping handlerMapping;

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        log.warn("Graphql 인터셉터 시작");

        // 요청된 API 필드 확인
        List<String> requestedFields = extractRequestedFields(request);
        log.warn("요청된 API 메서드들: " + requestedFields);

        for (String fieldName : requestedFields) {
            GraphqlHandlerMethod handlerMethod = handlerMapping.getHandlerMethod(fieldName);

            if (handlerMethod != null) {
                log.warn("매핑된 핸들러 메서드: {} -> {}", fieldName, handlerMethod.getMethod().getName());

                if (handlerMethod.hasMethodAnnotation(LoginOrNot.class)) {
                    log.warn("LoginOrNot 어노테이션 감지됨: " + fieldName);
                }
                if (handlerMethod.hasMethodAnnotation(AdminOnly.class)) {
                    log.warn("AdminOnly 어노테이션 감지됨: " + fieldName);
                }
                if (handlerMethod.hasMethodAnnotation(Authenticated.class)) {
                    log.warn("Authenticated 어노테이션 감지됨: " + fieldName);
                }
                if (handlerMethod.hasMethodAnnotation(CustomChecker.class)) {
                    log.warn("CustomChecker 어노테이션 감지됨: " + fieldName);
                    String bearer = request.getHeaders().getFirst(AUTHORIZATION);

                    if (bearer == null) {
                        authUpdater.updateCurrentUser(null);
                    } else {
                        String jwt = BearerTokenExtractor.extract(bearer);
                        ObjectId userId = jwtParser.getIdFromJwt(jwt);

                        User user = userReader.read(userId);

                        authUpdater.updateCurrentUser(user);
                    }
                }
            } else {
                log.warn("핸들러 메서드를 찾을 수 없음: " + fieldName);
            }
        }

        return chain.next(request);
    }


    private List<String> extractRequestedFields(WebGraphQlRequest request) {
        String documentString = request.getDocument();
        Document document = new Parser().parseDocument(documentString);

        return document.getDefinitions().stream()
                .filter(OperationDefinition.class::isInstance) // 오퍼레이션 정의만 가져오기
                .map(OperationDefinition.class::cast)
                .filter(op -> op.getName() == null || op.getName().equals(request.getOperationName())) // 현재 실행 중인 오퍼레이션만 선택
                .flatMap(op -> op.getSelectionSet().getSelections().stream()) // 선택된 필드 가져오기
                .filter(Field.class::isInstance) // 필드만 가져오기
                .map(Field.class::cast)
                .map(Field::getName) // 필드 이름 추출
                .collect(Collectors.toList());
    }

}