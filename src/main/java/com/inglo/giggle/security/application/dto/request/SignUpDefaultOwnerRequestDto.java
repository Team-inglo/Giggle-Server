package com.inglo.giggle.security.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.dto.request.AddressRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SignUpDefaultOwnerRequestDto(
        @JsonProperty("temporary_token")
        @NotNull(message = "임시 토큰은 필수입니다.")
        String temporaryToken,

        @JsonProperty("owner_info")
        SignUpDefaultOwnerOwnerInfo signUpDefaultOwnerOwnerInfo,

        @JsonProperty("address")
        AddressRequestDto address,

        @JsonProperty("marketing_allowed")
        @NotNull(message = "마케팅 정보 활용 동의 여부를 입력해주세요.")
        Boolean marketingAllowed,

        @JsonProperty("notification_allowed")
        @NotNull(message = "알림 수신 동의 여부를 입력해주세요.")
        Boolean notificationAllowed,

        @JsonProperty("term_types")
        @NotNull(message = "약관 동의는 필수입니다.")
        List<String> termTypes
) {
        public record SignUpDefaultOwnerOwnerInfo(
                @JsonProperty("company_name")
                @NotBlank(message = "회사/점포명을 입력해주세요.")
                String companyName,

                @JsonProperty("owner_name")
                @NotBlank(message = "이름을 입력해주세요.")
                String ownerName,

                @JsonProperty("company_registration_number")
                @NotBlank(message = "사업자 등록번호를 입력해주세요.")
                String companyRegistrationNumber,

                @JsonProperty("phone_number")
                @NotBlank(message = "전화번호를 입력해주세요.")
                String phoneNumber
        ){}
}
