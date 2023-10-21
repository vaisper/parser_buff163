package com.parser.parser.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Info {
    @JsonProperty("tags")
    private Tags tags;
}

