package com.oceaneeda.server.global.config;

import com.oceaneeda.server.global.converter.DateToLocalDateTimeKstConverter;
import com.oceaneeda.server.global.converter.LocalDateTimeToDateKstConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;

@Configuration
@EnableMongoAuditing
//@EnableTransactionManagement 기본적으로 활성되어 있음
public class MongoConfig {

    @Bean
    public MongoTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTransactionManager(mongoDatabaseFactory);
    }

    @Bean
    public MongoCustomConversions customConversions(
            LocalDateTimeToDateKstConverter localDateTimeToDateKstConverter,
            DateToLocalDateTimeKstConverter dateToLocalDateTimeKstConverter
    ) {
        return new MongoCustomConversions(
                Arrays.asList(
                        localDateTimeToDateKstConverter,
                        dateToLocalDateTimeKstConverter
                )
        );
    }
}
