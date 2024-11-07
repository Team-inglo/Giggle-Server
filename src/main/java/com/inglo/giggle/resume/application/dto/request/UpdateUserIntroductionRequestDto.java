package com.inglo.giggle.resume.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

public record UpdateUserIntroductionRequestDto(
        @JsonProperty("introduction")
        @Size(min = 1, max = 200, message = "자기소개는 1자 이상 200자 이하로 입력 가능합니다.")
        String introduction
) {
}
