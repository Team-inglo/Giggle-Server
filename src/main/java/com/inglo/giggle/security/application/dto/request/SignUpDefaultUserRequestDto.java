package com.inglo.giggle.security.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.dto.request.AddressRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record SignUpDefaultUserRequestDto(
        @JsonProperty("temporary_token")
        @NotNull
        String temporaryToken,

        @JsonProperty("user_info")
        SignUpDefaultUserUserInfo signUpDefaultUserUserInfo,

        @JsonProperty("address")
        AddressRequestDto address,

        @JsonProperty("marketing_allowed")
        @NotNull(message = "마케팅 정보 활용 동의 여부를 입력해주세요.")
        Boolean marketingAllowed,

        @JsonProperty("notification_allowed")
        @NotNull(message = "알림 수신 동의 여부를 입력해주세요.")
        Boolean notificationAllowed,

        @JsonProperty("language")
        @NotNull(message = "사용 언어 선택은 필수입니다.")
        String language,

        @JsonProperty("term_types")
        @NotNull(message = "약관 동의는 필수입니다.")
        List<String> termTypes
) {
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
        ) {}
}
