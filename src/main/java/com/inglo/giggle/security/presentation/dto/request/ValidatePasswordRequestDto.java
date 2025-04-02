package com.inglo.giggle.security.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ValidatePasswordRequestDto(
        @JsonProperty("password")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) {
}
