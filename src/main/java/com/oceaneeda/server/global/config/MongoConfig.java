package com.oceaneeda.server.global.config;

import com.oceaneeda.server.global.converter.DateToLocalDateTimeKstConverter;
import com.oceaneeda.server.global.converter.LocalDateTimeToDateKstConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

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
