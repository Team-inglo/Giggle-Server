package com.inglo.giggle.security.account.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record IssueAuthenticationCodeRequestDto(
        @JsonProperty("email")
        String email
) {
}
