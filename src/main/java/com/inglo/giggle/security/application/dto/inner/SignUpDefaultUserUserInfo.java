package com.inglo.giggle.security.application.dto.inner;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SignUpDefaultUserUserInfo(
        @JsonProperty("first_name")
        @NotBlank(message = "first name을 입력해주세요.")
        String firstName,

        @JsonProperty("last_name")
        @NotBlank(message = "last name을 입력해주세요.")
        String lastName,

        @JsonProperty("gender")
        @NotNull(message = "성별을 선택해주세요.")
        String gender,

        @JsonProperty("birth")
        @NotNull(message = "생년월일을 입력해주세요.")
        LocalDate birth,

        @JsonProperty("nationality")
        @NotBlank(message = "국적을 입력해주세요")
        String nationality,

        @JsonProperty("visa")
        @NotNull(message = "비자를 입력해주세요.")
        String visa,

        @JsonProperty("phone_number")
        @NotBlank(message = "전화번호를 입력해주세요.")
        String phoneNumber
) {
}
