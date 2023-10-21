package com.parser.parser.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TagDetail {
    @JsonProperty("category")
    private String category;
    @JsonProperty("id")
    private int id;
    @JsonProperty("internal_name")
    private String internalName;
    @JsonProperty("localized_name")
    private String localizedName;
}

