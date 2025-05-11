package com.inglo.giggle.user.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateUserSelfLanguageRequestDto(
        @JsonProperty("language")
        String language
) {
}
