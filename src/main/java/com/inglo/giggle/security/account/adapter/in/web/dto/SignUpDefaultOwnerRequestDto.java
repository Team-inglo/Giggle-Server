package com.inglo.giggle.security.account.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.AddressRequestDto;

import java.util.List;

public record SignUpDefaultOwnerRequestDto(
        @JsonProperty("temporary_token")
        String temporaryToken,

        @JsonProperty("owner_info")
        SignUpDefaultOwnerOwnerInfo signUpDefaultOwnerOwnerInfo,

        @JsonProperty("address")
        AddressRequestDto address,

        @JsonProperty("marketing_allowed")
        Boolean marketingAllowed,

        @JsonProperty("notification_allowed")
        Boolean notificationAllowed,

        @JsonProperty("term_types")
        List<String> termTypes
) {
        public record SignUpDefaultOwnerOwnerInfo(
                @JsonProperty("company_name")
                String companyName,

                @JsonProperty("owner_name")
                String ownerName,

                @JsonProperty("company_registration_number")
                String companyRegistrationNumber,

                @JsonProperty("phone_number")
                String phoneNumber
        ){}
}
