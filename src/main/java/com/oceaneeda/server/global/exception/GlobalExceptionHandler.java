package com.oceaneeda.server.global.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@Component
public class GlobalExceptionHandler extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof OceaneedaException oceaneedaException) {
            HttpStatus status = oceaneedaException.getStatus();

            return GraphqlErrorBuilder.newError()
                    .message(oceaneedaException.getMessage())
                    .errorType(graphql.ErrorType.DataFetchingException)
                    .path(env.getExecutionStepInfo().getPath())
                    .extensions(Map.of(
                            "errorCode", oceaneedaException.getErrorCode(),
                            "httpStatus", status.value(), // HTTP 상태 코드 추가
                            "httpStatusMessage", status.getReasonPhrase() // HTTP 상태 메시지 추가
                    ))
                    .build();
        } else if (ex instanceof IllegalArgumentException || ex instanceof MethodArgumentTypeMismatchException || ex instanceof BindException) {
            return GraphqlErrorBuilder.newError()
                    .message("입력 값이 올바르지 않습니다: " + ex.getMessage())
                    .errorType(graphql.ErrorType.ValidationError)
                    .path(env.getExecutionStepInfo().getPath())
                    .extensions(Map.of(
                            "errorCode", "INVALID_INPUT",
                            "httpStatus", HttpStatus.BAD_REQUEST.value(),
                            "httpStatusMessage", HttpStatus.BAD_REQUEST.getReasonPhrase()
                    ))
                    .build();
        } else if (ex instanceof DuplicateKeyException) {
            return GraphqlErrorBuilder.newError()
                    .message("값이 이미 존재합니다.: " + ex.getMessage())
                    .errorType(ErrorType.DataFetchingException)
                    .path(env.getExecutionStepInfo().getPath())
                    .extensions(Map.of(
                            "errorCode", "DUPLICATE_VALUE",
                            "httpStatus", HttpStatus.CONFLICT.value(),
                            "httpStatusMessage", HttpStatus.CONFLICT.getReasonPhrase()
                    ))
                    .build();
        }
        return null;
    }
}
