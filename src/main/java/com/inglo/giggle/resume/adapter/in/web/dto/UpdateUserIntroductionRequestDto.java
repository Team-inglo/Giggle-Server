package com.inglo.giggle.resume.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateUserIntroductionRequestDto(
        @JsonProperty("introduction")
        String introduction
) {
}
