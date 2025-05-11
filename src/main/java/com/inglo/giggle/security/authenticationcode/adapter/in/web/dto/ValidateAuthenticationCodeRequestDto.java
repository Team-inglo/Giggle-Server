package com.inglo.giggle.security.authenticationcode.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ValidateAuthenticationCodeRequestDto(
        @JsonProperty("email")
        String email,

        @JsonProperty("authentication_code")
        String authenticationCode
) {
}
