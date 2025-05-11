package com.inglo.giggle.term.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.term.domain.type.ETermType;

public record CreateAdminTermRequestDto(
        @JsonProperty("version")
        Double version,

        @JsonProperty("content")
        String content,

        @JsonProperty("term_type")
        ETermType termType
) {}
