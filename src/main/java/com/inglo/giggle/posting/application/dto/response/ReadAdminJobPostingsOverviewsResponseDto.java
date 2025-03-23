package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.CountInfoDto;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadAdminJobPostingsOverviewsResponseDto extends SelfValidating<ReadAdminJobPostingsOverviewsResponseDto> {

    @NotNull
    @JsonProperty("job_posting_count_info")
    private final CountInfoDto jobPostingCountInfo;

    @Builder
    public ReadAdminJobPostingsOverviewsResponseDto(
            CountInfoDto jobPostingCountInfo
    ) {
        this.jobPostingCountInfo = jobPostingCountInfo;

        this.validateSelf();
    }

    public static ReadAdminJobPostingsOverviewsResponseDto of(
            Integer count,
            Integer priorPeriodCount,
            Double priorPeriodComparison
    ) {
        return ReadAdminJobPostingsOverviewsResponseDto.builder()
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
