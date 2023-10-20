package com.parser.parser.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class InitConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public OkHttpClient OkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS) // Установка тайм-аута на подключение
                .writeTimeout(10, TimeUnit.SECONDS)   // Установка тайм-аута на запись
                .readTimeout(30, TimeUnit.SECONDS)    // Установка тайм-аута на чтение
                .build();
    }


}
