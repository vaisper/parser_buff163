package com.parser.parser.controller;

import com.generated.api.contract.ParserApi;
import com.parser.parser.service.MarketApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ParserController implements ParserApi {

    private final MarketApiService marketApiService;

    @Override
    public ResponseEntity<String> startParser() {
        CompletableFuture.runAsync(() -> {
            try {
                marketApiService.runScraper();
            } catch (Exception e) {
                log.error("Failed to start parser: " + e.getMessage());
            }
        });

        return ResponseEntity.ok("start parser");
    }
}
