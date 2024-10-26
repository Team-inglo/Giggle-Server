package com.inglo.giggle.posting.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserUserOwnerJobPostingStepRegisteringResultRequestDto(

        @NotNull
        @JsonProperty("is_approval")
        Boolean isApproval,

        @NotNull
        @Size(max = 200)
        @JsonProperty("feedback")
        String feedback
) {
}
