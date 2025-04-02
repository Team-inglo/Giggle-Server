package com.inglo.giggle.posting.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserUserOwnerJobPostingStepRegisteringResultRequestDto(

        @NotNull(message = "is_approval은 필수 입력 값입니다.")
        @JsonProperty("is_approval")
        Boolean isApproval,

        @NotNull(message = "feedback은 필수 입력 값입니다.")
        @Size(max = 200, message = "feedback은 최대 200자까지 입력 가능합니다.")
        @JsonProperty("feedback")
        String feedback
) {
}
