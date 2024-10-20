package com.inglo.giggle.security.dto.inner;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

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