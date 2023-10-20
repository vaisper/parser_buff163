package com.parser.parser.dto.response;

import lombok.Data;

@Data
public class Item {
    private int appid;
    private boolean bookmarked;
    private String buyMaxPrice;
    private int buyNum;
    private boolean canBargain;
    private boolean canSearchByTournament;
    private String description;
    private String game;
    private GoodsInfo goodsInfo;
    private boolean hasBuffPriceHistory;
    private int id;
    private String marketHashName;
    private String marketMinPrice;
    private String name;
    private String quickPrice;
    private String sellMinPrice;
    private int sellNum;
    private String sellReferencePrice;
    private String shortName;
    private String steamMarketUrl;
    private int transactedNum;
}

