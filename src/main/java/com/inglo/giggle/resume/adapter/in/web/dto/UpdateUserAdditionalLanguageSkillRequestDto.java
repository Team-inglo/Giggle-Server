package com.inglo.giggle.resume.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateUserAdditionalLanguageSkillRequestDto(
        @JsonProperty("language_name")
        String languageName,

        @JsonProperty("level")
        Integer level
) {
}
