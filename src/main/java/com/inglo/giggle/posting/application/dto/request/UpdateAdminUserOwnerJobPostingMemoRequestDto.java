package com.inglo.giggle.posting.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

public record UpdateAdminUserOwnerJobPostingMemoRequestDto(

        @JsonProperty("memo")
        @Size(max = 200, message = "메모는 200자 이내로 작성해주세요.")
        String memo
) {
}
