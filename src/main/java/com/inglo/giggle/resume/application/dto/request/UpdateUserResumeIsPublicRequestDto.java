package com.inglo.giggle.resume.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateUserResumeIsPublicRequestDto(

        @NotNull(message = "is_public은 필수 입력 값입니다.")
        @JsonProperty("is_public")
        boolean isPublic
) {
}
