package com.parser.parser.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parser.parser.config.PropertyConfig;
import com.parser.parser.dto.response.JsonResponse;
import com.parser.parser.dto.response.ResponseData;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Optional;
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
        Instant start = Instant.now();

        log.info("Start scrap");
        Optional<Integer> totalPages = fetchTotalPages();
        int pages = 0;
        if (totalPages.isPresent()) {
            pages = totalPages.get();
        } else {
            log.error("Failed to get total pages.");
            System.exit(1);
        }

        fetchPages(pages);

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        log.info("Finish scrap. It took " + getFinalTime(timeElapsed));
    }

    private void fetchPages(int totalPages) {
        for (int i = 1; i <= totalPages; i++) {
            fetchPage(i);

            if (i < totalPages) {
                try {
                    TimeUnit.SECONDS.sleep(propertyConfig.getDelaySeconds());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.info("Interrupted. Exiting early.");
                    break;
                }
            }
        }
    }

    @SneakyThrows
    private void fetchPage(int page) {

        Request request = MarketApiBuilder.obtainRequest(propertyConfig.getCookie(), page);
        ResponseData data = getJsonResponse(page, request);

        log.info("Сохраняем в базу");
        log.info("-------------------------------------------------------------------------------------");
        TimeUnit.SECONDS.sleep(2);
    }

    private ResponseData getJsonResponse(int page, Request request) {
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null)
                throw new IOException("Unexpected code " + response);

            String body = response.body().string();
            JsonResponse stickersResponse = mapper.readValue(body, JsonResponse.class);
            log.info("scrap page: " + page);
            return stickersResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Optional<Integer> fetchTotalPages() {
        Request request = MarketApiBuilder.obtainRequest(propertyConfig.getCookie(), 1);

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null)
                throw new IOException("Unexpected code " + response);

            String body = response.body().string();
            if (body.trim().startsWith("<")) {
                throw new IOException("Received unexpected HTML response. Possible authentication issue.");
            }
            JsonResponse stickersResponse = mapper.readValue(body, JsonResponse.class);
            int totalPage = stickersResponse.getData().getTotalPage();
            log.info("get total page: " + totalPage);
            return Optional.of(totalPage);
        } catch (IOException e) {
            log.error("Error occurred while fetching total pages: ", e);
        }
        return Optional.empty();
    }

    private static LocalTime getFinalTime(long timeElapsed) {
        long totalSeconds = timeElapsed / 1000;
        int hours = (int) (totalSeconds / 3600);
        totalSeconds -= hours * 3600L;
        int minutes = (int) (totalSeconds / 60);
        int seconds = (int) (totalSeconds % 60);
        return LocalTime.of(hours, minutes, seconds);
    }

}
