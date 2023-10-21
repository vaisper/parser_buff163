package com.parser.parser.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.Request;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class MarketApiBuilder {

    public static Request obtainRequest(String cookie, int page) {
        return new Request.Builder()
                .addHeader(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .addHeader(HttpHeaders.REFERER, "https://buff.163.com/")
                .addHeader(HttpHeaders.COOKIE, cookie)
                .url(obtainUr(page))
                .build();

    }

    private static HttpUrl obtainUr(int page) {
        return new HttpUrl.Builder()
                .scheme("https")
                .host("buff.163.com")
                .addPathSegments("api/market/goods")
                .addQueryParameter("category_group", "sticker")
                .addQueryParameter("game", "csgo")
                .addQueryParameter("page_num", String.valueOf(page))
                .addQueryParameter("game", "csgo")
                .addQueryParameter("sort_by", "price.desc")
                .build();
    }
}
