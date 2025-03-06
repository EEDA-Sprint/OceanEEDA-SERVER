package com.oceaneeda.server.global.config;

import com.oceaneeda.server.domain.auth.handler.GraphqlHandlerMapping;
import com.oceaneeda.server.domain.auth.interceptor.GraphqlInterceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphqlInterceptorConfig {
    @Bean
    public GraphqlHandlerMapping graphqlHandlerMapping(ApplicationContext applicationContext) {
        return new GraphqlHandlerMapping(applicationContext);
    }

//    @Bean
//    public GraphqlInterceptor graphqlInterceptor(GraphqlHandlerMapping handlerMapping) {
//        return new GraphqlInterceptor(handlerMapping);
//    }
}
