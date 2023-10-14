package com.parser.parser.dto.response;

import lombok.Data;

@Data
public class GoodsInfo {
    private String iconUrl;
    private Info info;
    private String itemId;
    private String originalIconUrl;
    private String steamPrice;
    private String steamPriceCny;
}

