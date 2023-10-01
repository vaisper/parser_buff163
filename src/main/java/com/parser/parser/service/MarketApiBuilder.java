package com.parser.parser.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
@Slf4j
@Service
@RequiredArgsConstructor
public class MarketApiBuilder {

    public static OkHttpClient obtainHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS) // Установка тайм-аута на подключение
                .writeTimeout(10, TimeUnit.SECONDS)   // Установка тайм-аута на запись
                .readTimeout(30, TimeUnit.SECONDS)    // Установка тайм-аута на чтение
                .build();
    }

    public static Request obtainRequest(String cookie, int page) {
        return new Request.Builder()
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .addHeader("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                .addHeader("Connection", "keep-alive")
                .addHeader("Referrer", "https://buff.163.com/")
                .addHeader("Cookie", cookie)
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("Sec-Fetch-Dest", "empty")
                .addHeader("Sec-Fetch-Mode", "cors")
                .addHeader("Sec-Fetch-Site", "same-origin")
                .url(obtainUr(page))
                .build();

    }

    private static HttpUrl obtainUr(int page) {
        return new HttpUrl.Builder()
                .scheme("https")
                .host("buff.163.com")
//                .addPathSegments("api/market/goods")
                .addPathSegment("api")
                .addPathSegment("market")
                .addPathSegment("goods")
                .addQueryParameter("category_group", "sticker")
                .addQueryParameter("game", "csgo")
                .addQueryParameter("page_num", String.valueOf(page))
                .addQueryParameter("game", "csgo")
                .build();
    }
}
