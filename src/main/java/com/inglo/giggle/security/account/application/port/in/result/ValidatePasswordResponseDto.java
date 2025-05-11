package com.inglo.giggle.security.account.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ValidatePasswordResponseDto extends SelfValidating<ValidatePasswordResponseDto> {
    @JsonProperty("is_valid")
    @NotNull(message = "비밀번호 유효성은 필수입니다.")
    private final Boolean isValid;

    @Builder
    public ValidatePasswordResponseDto(Boolean isValid) {
        this.isValid = isValid;

        validateSelf();
    }

    public static ValidatePasswordResponseDto of(Boolean isValid) {
        return ValidatePasswordResponseDto.builder()
                .isValid(isValid)
                .build();
    }
}
