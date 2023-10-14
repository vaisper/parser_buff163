package com.parser.parser.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parser.parser.config.PropertyConfig;
import com.parser.parser.dto.response.JsonResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketApiService {
    private final PropertyConfig propertyConfig;
    private final ObjectMapper mapper;
    private final OkHttpClient okHttpClient;

    @PostConstruct
    public void init() {
        runScraper();
    }

    public void runScraper() {
        System.out.println("НАЧАЛ СОБИРАТЬ");
        fetchPages(3);
        System.out.println("ЗАКОНЧИЛ СОБИРАТЬ");

    }

    public void fetchPages(int totalPages) {
        for (int i = 1; i <= totalPages; i++) {
            fetchPage(i);

            if (i < totalPages) {
                try {
                    TimeUnit.SECONDS.sleep(propertyConfig.getDelaySeconds());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupted. Exiting early.");
                    break;
                }
            }
        }
    }

    private void fetchPage(int page) {

        Request request = MarketApiBuilder.obtainRequest(propertyConfig.getCookie(), page);

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() != null)
                throw new IOException("Unexpected code " + response);

            String body = response.body().string();
            JsonResponse stickersResponse = mapper.readValue(body, JsonResponse.class);

            System.out.println("сохраняем в базу");
            String responseData = response.body().string();
            System.out.println("Получил данные страницы: " + page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
