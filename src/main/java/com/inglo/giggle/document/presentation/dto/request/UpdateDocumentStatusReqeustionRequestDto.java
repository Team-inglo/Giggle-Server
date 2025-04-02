package com.inglo.giggle.document.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateDocumentStatusReqeustionRequestDto(

        @NotNull(message = "재요청 사유는 필수입니다.")
        @Size(min = 1, max = 100, message = "재요청 사유는 1자 이상 100자 이하로 입력해주세요.")
        @JsonProperty("reason")
        String reason
) {
}
