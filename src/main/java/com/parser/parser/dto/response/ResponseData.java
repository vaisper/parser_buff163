package com.parser.parser.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ResponseData {
    private List<Item> items;
    private int pageNum;
    private int pageSize;
    private int totalCount;
    private int totalPage;
}

