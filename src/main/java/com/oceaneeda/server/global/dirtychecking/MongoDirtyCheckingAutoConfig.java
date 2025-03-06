package com.oceaneeda.server.global.dirtychecking;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoDirtyCheckingAutoConfig {
    @Bean
    public MongoPersistenceContext mongoPersistenceContext() {
        return new MongoPersistenceContext();
    }

    @Bean
    public MongoEntityLoadAspect mongoEntityLoadAspect(MongoPersistenceContext persistenceContext) {
        return new MongoEntityLoadAspect(persistenceContext);
    }

    @Bean
    public MongoDirtyCheckingAspect mongoDirtyCheckingAspect(MongoPersistenceContext persistenceContext, ApplicationContext applicationContext) {
        return new MongoDirtyCheckingAspect(persistenceContext, applicationContext);
    }
}
