package com.inglo.giggle.security.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public class ValidationResponseDto extends SelfValidating<ValidationResponseDto> {
    @JsonProperty("is_valid")
    @NotNull
    private final Boolean isValid;

    @Builder
    public ValidationResponseDto(Boolean isValid) {
        this.isValid = isValid;
        this.validateSelf();
    }

    public static ValidationResponseDto of(Boolean isValid) {
        return ValidationResponseDto.builder()
                .isValid(isValid)
                .build();
    }
}