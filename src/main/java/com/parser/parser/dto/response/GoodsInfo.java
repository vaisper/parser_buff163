package com.parser.parser.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GoodsInfo {
    @JsonProperty("icon_url")
    private String iconUrl;
    @JsonProperty("info")
    private Info info;
    @JsonProperty("item_id")
    private String itemId;
    @JsonProperty("original_icon_url")
    private String originalIconUrl;
    @JsonProperty("steam_price")
    private String steamPrice;
    @JsonProperty("steam_price_cny")
    private String steamPriceCny;
}

