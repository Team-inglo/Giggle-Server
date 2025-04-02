package com.inglo.giggle.account.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateUserLanguageRequestDto(
        @JsonProperty("language")
        String language
) {
}
