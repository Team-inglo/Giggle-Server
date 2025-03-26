package com.inglo.giggle.resume.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateUserSejongInstituteReqeustDto(
        @NotNull(message = "level은 필수 입력 값입니다.")
        @Max(value = 6, message = "level은 6 이하로 입력 가능합니다.")
        @Min(value = 0, message = "level은 0 이상으로 입력 가능합니다.")
        @JsonProperty("level")
        Integer level
) {
}
