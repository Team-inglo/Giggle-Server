package com.inglo.giggle.resume.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

public record UpdateUserIntroductionRequestDto(
        @JsonProperty("introduction")
        @Size(min = 1, max = 200)
        String introduction
) {
}
