package com.parser.parser.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Item {
    @JsonProperty("appid")
    private int appid;
    @JsonProperty("bookmarked")
    private boolean bookmarked;
    @JsonProperty("buy_max_price")
    private double buyMaxPrice;
    @JsonProperty("buy_num")
    private int buyNum;
    @JsonProperty("can_bargain")
    private boolean canBargain;
    @JsonProperty("can_search_by_tournament")
    private boolean canSearchByTournament;
    @JsonProperty("description")
    private String description;
    @JsonProperty("game")
    private String game;
    @JsonProperty("goods_info")
    private GoodsInfo goodsInfo;
    @JsonProperty("has_buff_price_history")
    private boolean hasBuffPriceHistory;
    @JsonProperty("id")
    private int id;
    @JsonProperty("market_hash_name")
    private String marketHashName;
    @JsonProperty("market_min_price")
    private String marketMinPrice;
    @JsonProperty("name")
    private String name;
    @JsonProperty("quick_price")
    private String quickPrice;
    @JsonProperty("sell_min_price")
    private String sellMinPrice;
    @JsonProperty("sell_num")
    private int sellNum;
    @JsonProperty("sell_reference_price")
    private String sellReferencePrice;
    @JsonProperty("short_name")
    private String shortName;
    @JsonProperty("steam_market_url")
    private String steamMarketUrl;
    @JsonProperty("transacted_num")
    private int transactedNum;

}

