package com.inglo.giggle.security.account.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ValidationResult extends SelfValidating<ValidationResult> {
    @JsonProperty("is_valid")
    @NotNull
    private final Boolean isValid;

    @Builder
    public ValidationResult(Boolean isValid) {
        this.isValid = isValid;
        this.validateSelf();
    }

    public static ValidationResult of(Boolean isValid) {
        return ValidationResult.builder()
                .isValid(isValid)
                .build();
    }
}