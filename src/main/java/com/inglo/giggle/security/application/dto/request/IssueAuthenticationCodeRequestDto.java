package com.inglo.giggle.security.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record IssueAuthenticationCodeRequestDto(
        @JsonProperty("email")
        @NotBlank(message = "이메일 정보가 존재하지 않습니다.")
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
                message = "이메일 형식이 올바르지 않습니다.")
        String email
) {
}
