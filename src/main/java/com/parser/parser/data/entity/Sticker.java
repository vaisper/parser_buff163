package com.parser.parser.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sticker {

    @JsonProperty("short_name")
    private String name;
    @JsonProperty("sell_min_price")
    private Double sellMinPrice;
    @JsonProperty("quick_price")
    private Double quickPrice;
    @JsonProperty("steam_market_url")
    private String steamMarketUrl;


}
