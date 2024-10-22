package com.inglo.giggle.security.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ValidationResponseDto(
        @JsonProperty("is_valid")
        Boolean isValid
) {
        public static ValidationResponseDto of(Boolean isValid) {
                return ValidationResponseDto.builder()
                        .isValid(isValid)
                        .build();
        }
}
