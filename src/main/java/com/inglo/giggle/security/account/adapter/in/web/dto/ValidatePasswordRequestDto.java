package com.inglo.giggle.security.account.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ValidatePasswordRequestDto(
        @JsonProperty("password")
        String password
) {
}
