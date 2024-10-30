package com.inglo.giggle.resume.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserAdditionalLanguageSkillRequestDto(
        @JsonProperty("language_name")
        @Size(min = 1, max = 50)
        @NotBlank
        String languageName,

        @JsonProperty("level")
        Integer level
) {
}
