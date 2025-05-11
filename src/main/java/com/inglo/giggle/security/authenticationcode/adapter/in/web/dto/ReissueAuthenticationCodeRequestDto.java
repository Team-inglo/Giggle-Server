package com.inglo.giggle.security.authenticationcode.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReissueAuthenticationCodeRequestDto(
        @JsonProperty("email")
        String email
) {
}
