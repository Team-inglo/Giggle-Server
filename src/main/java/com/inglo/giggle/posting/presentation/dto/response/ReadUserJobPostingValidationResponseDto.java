package com.inglo.giggle.posting.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadUserJobPostingValidationResponseDto extends SelfValidating<ReadUserJobPostingValidationResponseDto> {

    @NotNull
    @JsonProperty("is_qualification_verified")
    private final Boolean isQualificationVerified;

    @Builder
    public ReadUserJobPostingValidationResponseDto(Boolean isQualificationVerified) {
        this.isQualificationVerified = isQualificationVerified;

        this.validateSelf();
    }

    public static ReadUserJobPostingValidationResponseDto of(Boolean isQualificationVerified) {
        return ReadUserJobPostingValidationResponseDto.builder()
                .isQualificationVerified(isQualificationVerified)
                .build();
    }
}
