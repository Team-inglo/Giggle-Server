package com.inglo.giggle.term.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CreateTermAccountRequestDto(
        @JsonProperty("term_types")
        List<String> termTypes
) {
}
