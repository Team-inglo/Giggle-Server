package com.inglo.giggle.posting.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.CountInfoDto;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadAdminJobPostingsSummariesResponseDto extends SelfValidating<ReadAdminJobPostingsSummariesResponseDto> {

    @NotNull
    @JsonProperty("job_posting_count_info")
    private final CountInfoDto jobPostingCountInfo;

    @Builder
    public ReadAdminJobPostingsSummariesResponseDto(
            CountInfoDto jobPostingCountInfo
    ) {
        this.jobPostingCountInfo = jobPostingCountInfo;

        this.validateSelf();
    }

    public static ReadAdminJobPostingsSummariesResponseDto of(
            Integer count,
            Integer priorPeriodCount,
            Double priorPeriodComparison
    ) {
        return ReadAdminJobPostingsSummariesResponseDto.builder()
                .jobPostingCountInfo(
                        CountInfoDto.of(
                                count,
                                priorPeriodCount,
                                priorPeriodComparison
                        )
                )
                .build();
    }
}
