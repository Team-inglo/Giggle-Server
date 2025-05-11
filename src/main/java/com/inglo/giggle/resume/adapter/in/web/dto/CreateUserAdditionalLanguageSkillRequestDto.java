package com.inglo.giggle.resume.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateUserAdditionalLanguageSkillRequestDto(
        @JsonProperty("language_name")
        String languageName,

        @JsonProperty("level")
        Integer level
) {
}
