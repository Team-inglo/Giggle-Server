package com.inglo.giggle.resume.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public record UpdateUserAdditionalLanguageSkillRequestDto(
        @JsonProperty("language_name")
        @Size(min = 1, max = 50, message = "언어 이름은 1자 이상 50자 이하로 입력 가능합니다.")
        @NotBlank(message = "언어 이름은 필수 입력 값입니다.")
        String languageName,

        @JsonProperty("level")
        @NotNull(message = "언어 레벨은 필수 입력 값입니다.")
        @Max(value = 10, message = "언어 레벨은 10 이하로 입력 가능합니다.")
        @Min(value = 0, message = "언어 레벨은 0 이상으로 입력 가능합니다.")
        Integer level
) {
}
