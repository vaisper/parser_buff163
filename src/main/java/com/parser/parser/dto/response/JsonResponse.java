package com.parser.parser.dto.response;

import lombok.Data;

@Data
public class JsonResponse {
    private String code;
    private ResponseData data;
    private String msg;
}

