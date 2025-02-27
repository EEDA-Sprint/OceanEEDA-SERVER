package com.oceaneeda.server.global.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

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
        }
        return null;
    }
}
