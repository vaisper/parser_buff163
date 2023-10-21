package com.parser.parser.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseData {
    @JsonProperty("items")
    private List<Item> items;
    @JsonProperty("page_num")
    private int pageNum;
    @JsonProperty("page_size")
    private int pageSize;
    @JsonProperty("total_count")
    private int totalCount;
    @JsonProperty("total_page")
    private int totalPage;
}

