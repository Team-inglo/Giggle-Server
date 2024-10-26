package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadUserOwnerJobPostingCountResponseDto extends SelfValidating<ReadUserOwnerJobPostingCountResponseDto> {

    @NotNull(message = "application_counts는 null일 수 없습니다.")
    @JsonProperty("application_counts")
    private final Integer applicationCounts;

    @NotNull(message = "successful_hire_counts는 null일 수 없습니다.")
    @JsonProperty("successful_hire_counts")
    private final Integer successfulHireCounts;

    @Builder
    public ReadUserOwnerJobPostingCountResponseDto(
            Integer applicationCounts,
            Integer successfulHireCounts
    ) {
        this.applicationCounts = applicationCounts;
        this.successfulHireCounts = successfulHireCounts;

        this.validateSelf();
    }

    public static ReadUserOwnerJobPostingCountResponseDto of(Integer applicationCounts, Integer successfulHireCounts) {
        return ReadUserOwnerJobPostingCountResponseDto.builder()
                .applicationCounts(applicationCounts)
                .successfulHireCounts(successfulHireCounts)
                .build();
    }
}
