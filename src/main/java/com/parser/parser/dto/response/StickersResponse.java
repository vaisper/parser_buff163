package com.parser.parser.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parser.parser.data.entity.Sticker;

import java.util.List;

public class StickersResponse {
@JsonProperty("items")
    private List<Sticker> stickers;
}
