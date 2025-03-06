package com.oceaneeda.server.global.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Collections;

@Component
public class CustomExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // GraphQL 오류를 위한 형식
            if (ex instanceof OceaneedaException oceaneedaException) {
                GraphQLError error = GraphqlErrorBuilder.newError()
                        .message(oceaneedaException.getMessage())
                        .errorType(graphql.ErrorType.DataFetchingException) // 오류 유형 설정
                        .extensions(Collections.singletonMap(
                                "httpStatus", oceaneedaException.getStatus().value()))
                        .path(Collections.singletonList(request.getRequestURI())) // 예시에서는 URI로 path 설정
                        .extensions(Collections.singletonMap(
                                "errorCode", oceaneedaException.getErrorCode()))
                        .build();

                response.setStatus(oceaneedaException.getStatus().value());
                response.getWriter().write("{\"errors\": [{\"message\": \"" + oceaneedaException.getMessage() + "\", " +
                        "\"extensions\": {\"httpStatus\": " + oceaneedaException.getStatus().value() + ", " +
                        "\"errorCode\": \"" + oceaneedaException.getErrorCode() + "\", " +
                        "\"httpStatusMessage\": \"" + oceaneedaException.getStatus().getReasonPhrase() + "\", " +
                        "\"classification\": \"DataFetchingException\"}}], \"data\": null}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"errors\": [{\"message\": \"서버 내부 오류\", \"extensions\": {\"httpStatus\": 500, " +
                        "\"httpStatusMessage\": \"Internal Server Error\"}}], \"data\": null}");
            }
            return new ModelAndView(); // 추가적인 처리를 막음
        } catch (IOException e) {
            return null;
        }
    }
}
