package com.inglo.giggle.security.account.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChangePasswordRequestDto(
        @JsonProperty("current_password")
        String currentPassword,

        @JsonProperty("new_password")
        String newPassword
) {
}
