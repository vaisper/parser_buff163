package com.parser.parser.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Tags {
    @JsonProperty("quality")
    private TagDetail quality;
    @JsonProperty("rarity")
    private TagDetail rarity;
    @JsonProperty("type")
    private TagDetail type;
}

