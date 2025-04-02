package com.inglo.giggle.posting.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewRequestDto(

        @NotNull(message = "is_accepted는 필수 입력 값입니다.")
        @JsonProperty("is_accepted")
        Boolean isAccepted
) {
}
