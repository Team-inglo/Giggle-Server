package com.inglo.giggle.resume.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadUserResumeCompletionRateResponseDto extends SelfValidating<ReadUserResumeCompletionRateResponseDto> {

    @JsonProperty("completion_rate")
    @NotNull(message = "completionRate는 필수입니다.")
    private final Integer completionRate;

    @JsonProperty("completion_text")
    @NotNull(message = "completionText는 필수입니다.")
    private final String completionText;

    @Builder
    public ReadUserResumeCompletionRateResponseDto(Integer completionRate) {
        this.completionRate = completionRate;
        this.completionText = "이력서를 완성하면 연락 올 확률이 90% 올라가요!";
        this.validateSelf();
    }
}
