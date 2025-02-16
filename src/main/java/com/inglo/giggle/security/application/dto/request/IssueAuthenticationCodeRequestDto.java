package com.inglo.giggle.security.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IssueAuthenticationCodeRequestDto(
        @JsonProperty("email")
        @NotBlank(message = "이메일 정보가 존재하지 않습니다.")
        String email
) {
}
