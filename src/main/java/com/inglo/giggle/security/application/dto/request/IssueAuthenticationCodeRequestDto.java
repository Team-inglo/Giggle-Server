package com.inglo.giggle.security.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IssueAuthenticationCodeRequestDto(
        @JsonProperty("id")
        @NotBlank(message = "아이디 정보가 존재하지 않습니다.")
        String id,

        @JsonProperty("email")
        @NotBlank(message = "이메일 정보가 존재하지 않습니다.")
        String email
) {
}
