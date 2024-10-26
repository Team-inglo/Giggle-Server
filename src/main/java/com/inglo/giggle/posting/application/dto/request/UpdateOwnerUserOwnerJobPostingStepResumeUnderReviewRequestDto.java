package com.inglo.giggle.posting.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewRequestDto(

        @NotNull
        @JsonProperty("is_accepted")
        Boolean isAccepted
) {
}
