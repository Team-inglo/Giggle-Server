package com.inglo.giggle.security.temporaryaccount.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SignUpDefaultTemporaryRequestDto(

        @JsonProperty("password")
        String password,

        @JsonProperty("email")
        String email,

        @JsonProperty("account_type")
        String accountType
) {
}
