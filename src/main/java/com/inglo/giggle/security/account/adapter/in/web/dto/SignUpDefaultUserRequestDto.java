package com.inglo.giggle.security.account.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.AddressRequestDto;

import java.time.LocalDate;
import java.util.List;

public record SignUpDefaultUserRequestDto(
        @JsonProperty("temporary_token")
        String temporaryToken,

        @JsonProperty("user_info")
        SignUpDefaultUserUserInfo signUpDefaultUserUserInfo,

        @JsonProperty("address")
        AddressRequestDto address,

        @JsonProperty("marketing_allowed")
        Boolean marketingAllowed,

        @JsonProperty("notification_allowed")
        Boolean notificationAllowed,

        @JsonProperty("language")
        String language,

        @JsonProperty("term_types")
        List<String> termTypes
) {
        public record SignUpDefaultUserUserInfo(
                @JsonProperty("first_name")
                String firstName,

                @JsonProperty("last_name")
                String lastName,

                @JsonProperty("gender")
                String gender,

                @JsonProperty("birth")
                LocalDate birth,

                @JsonProperty("nationality")
                String nationality,

                @JsonProperty("visa")
                String visa,

                @JsonProperty("phone_number")
                String phoneNumber
        ) {}
}
