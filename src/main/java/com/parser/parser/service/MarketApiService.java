package com.parser.parser.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parser.parser.config.PropertyConfig;
import com.parser.parser.data.entity.Stickers;
import com.parser.parser.data.repo.StickersRepository;
import com.parser.parser.dto.response.Item;
import com.parser.parser.dto.response.JsonResponse;
import com.parser.parser.dto.response.ResponseData;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.parser.parser.data.entity.Stickers.convert;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketApiService {
    private final PropertyConfig propertyConfig;
    private final ObjectMapper mapper;
    private final OkHttpClient okHttpClient;
    private final StickersRepository stickersRepository;

    public void runScraper() {
        Instant start = Instant.now();

        log.info("Start scrap");
        // todo: сообщение в телегу
        Optional<Integer> totalPages = fetchTotalPages();
        int pages = 0;
        if (totalPages.isPresent()) {
            pages = totalPages.get();
        } else {
            log.error("Failed to get total pages.");
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
        List<Item> itemList = Objects.requireNonNull(data).getItems();

        log.info("Сохраняем в базу");
        itemList.forEach(item -> saveOrUpdateStickers(convert(item)));
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
//                todo: когда прикрутим телегу, добавить здесь сообщение в телегу
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

    public void saveOrUpdateStickers(Stickers stickers) {
        Stickers existingStickers = stickersRepository.findByGlobalItemId(stickers.getGlobalItemId());
        if (existingStickers == null) {
            // Записи с таким globalItemId не существует, вы можете сохранить входящую запись
            stickersRepository.save(stickers);
        } else {
            // Запись с таким globalItemId уже существует, выполните здесь обновление
            if (!existingStickers.getId().equals(stickers.getId())) {
                // Обновите, только если id не совпадают
                existingStickers.setShortName(stickers.getShortName());
                // Добавьте другие поля, которые вам нужно обновить
                stickersRepository.save(existingStickers); // Сохранить обновленную запись
            }
        }
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

