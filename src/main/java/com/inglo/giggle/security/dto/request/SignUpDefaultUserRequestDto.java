package com.inglo.giggle.security.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.AddressDto;
import com.inglo.giggle.security.dto.inner.SignUpDefaultUserUserInfo;
import jakarta.validation.constraints.NotNull;

public record SignUpDefaultUserRequestDto(
        @JsonProperty("temporary_token")
        @NotNull
        String temporaryToken,

        @JsonProperty("user_info")
        SignUpDefaultUserUserInfo signUpDefaultUserUserInfo,

        @JsonProperty("address")
        AddressDto address,

        @JsonProperty("marketing_allowed")
        @NotNull(message = "마케팅 정보 활용 동의 여부를 입력해주세요.")
        Boolean marketingAllowed,

        @JsonProperty("notification_allowed")
        @NotNull(message = "알림 수신 동의 여부를 입력해주세요.")
        Boolean notificationAllowed,

        @JsonProperty("language")
        @NotNull(message = "사용 언어 선택은 필수입니다.")
        String language
) {
}
