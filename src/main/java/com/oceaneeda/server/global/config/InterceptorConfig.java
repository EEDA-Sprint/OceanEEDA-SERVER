package com.oceaneeda.server.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private static final String ALLOW_ALL_PATH = "/**";
    private static final String ALLOWED_METHODS = "*";
    private static final String MAIN_SERVER_DOMAIN = "https://oceaneeda.com";
    private static final String FRONTEND_LOCALHOST = "http://localhost:3000";
    private static final String HTTPS_FRONTEND_LOCALHOST = "https://localhost:3000";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(ALLOW_ALL_PATH)
                .allowedMethods(ALLOWED_METHODS)
                .allowedOrigins(
                        MAIN_SERVER_DOMAIN,
                        FRONTEND_LOCALHOST,
                        HTTPS_FRONTEND_LOCALHOST
                )
                .exposedHeaders(AUTHORIZATION);
    }
}