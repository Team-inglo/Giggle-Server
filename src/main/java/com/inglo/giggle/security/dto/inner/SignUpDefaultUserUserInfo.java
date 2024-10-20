package com.inglo.giggle.security.dto.inner;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.domain.type.EGender;
import com.inglo.giggle.core.domain.type.EVisa;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

        @Enumerated(EnumType.STRING)
        @JsonProperty("gender")
        @NotNull(message = "성별을 선택해주세요.")
        EGender gender,

        @JsonProperty("birth")
        @NotNull(message = "생년월일을 입력해주세요.")
        LocalDate birth,

        @JsonProperty("nationality")
        @NotBlank(message = "국적을 입력해주세요")
        String nationality,

        @Enumerated(EnumType.STRING)
        @JsonProperty("visa")
        @NotNull(message = "비자를 입력해주세요.")
        EVisa visa,

        @JsonProperty("country_code")
        @NotBlank(message = "국가 코드는 필수입니다.")
        String countryCode,

        @JsonProperty("phone_number")
        @NotBlank(message = "전화번호를 입력해주세요.")
        String phoneNumber
) {
}
