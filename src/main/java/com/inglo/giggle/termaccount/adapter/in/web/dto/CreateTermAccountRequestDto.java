package com.inglo.giggle.termaccount.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CreateTermAccountRequestDto(
        @JsonProperty("term_types")
        List<String> termTypes
) {
}
