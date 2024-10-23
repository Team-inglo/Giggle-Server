package com.inglo.giggle.security.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.dto.AddressDto;
import com.inglo.giggle.security.application.dto.inner.SignUpDefaultOwnerOwnerInfo;
import jakarta.validation.constraints.NotNull;

public record SignUpDefaultOwnerRequestDto(
        @JsonProperty("temporary_token")
        @NotNull(message = "임시 토큰은 필수입니다.")
        String temporaryToken,

        @JsonProperty("owner_info")
        SignUpDefaultOwnerOwnerInfo signUpDefaultOwnerOwnerInfo,

        @JsonProperty("address")
        AddressDto address,

        @JsonProperty("marketing_allowed")
        @NotNull(message = "마케팅 정보 활용 동의 여부를 입력해주세요.")
        Boolean marketingAllowed,

        @JsonProperty("notification_allowed")
        @NotNull(message = "알림 수신 동의 여부를 입력해주세요.")
        Boolean notificationAllowed
) {
}
