package com.lpatros.ecommerce_api.configuration;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter DATETIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Bean
    public SimpleModule javaTimeModule() {
        SimpleModule module = new SimpleModule();

        module.addSerializer(
                LocalDate.class,
                new LocalDateSerializer(DATE_FORMATTER)
        );

        module.addDeserializer(
                LocalDate.class,
                new LocalDateDeserializer(DATE_FORMATTER)
        );

        module.addSerializer(
                LocalDateTime.class,
                new LocalDateTimeSerializer(DATETIME_FORMATTER)
        );

        module.addDeserializer(
                LocalDateTime.class,
                new LocalDateTimeDeserializer(DATETIME_FORMATTER)
        );

        return module;
    }
}
