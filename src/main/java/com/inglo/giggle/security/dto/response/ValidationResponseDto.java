package com.inglo.giggle.security.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ValidationResponseDto(
        @JsonProperty("is_valid")
        Boolean isValid
) {
}
