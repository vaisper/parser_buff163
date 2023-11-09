package com.parser.parser.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parser.parser.data.entity.Stickers;

import java.util.List;

public class StickersResponse {
@JsonProperty("items")
    private List<Stickers> stickers;
}
