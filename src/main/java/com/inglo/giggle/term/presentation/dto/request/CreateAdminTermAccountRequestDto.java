package com.inglo.giggle.term.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.term.domain.type.ETermType;
import jakarta.validation.constraints.NotNull;

public record CreateAdminTermAccountRequestDto(
        @JsonProperty("version")
        @NotNull(message = "version은 필수값입니다.")
        Double version,

        @JsonProperty("content")
        @NotNull(message = "content은 필수값입니다.")
        String content,

        @JsonProperty("term_type")
        @NotNull(message = "term_type은 필수값입니다.")
        ETermType termType
) {}
