package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.CountInfoDto;
import com.inglo.giggle.core.dto.SelfValidating;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadAdminUserOwnerJobPostingsSummariesResponseDto extends SelfValidating<ReadAdminUserOwnerJobPostingsSummariesResponseDto> {

    @JsonProperty("user_owner_job_postings_info")
    private final CountInfoDto userOwnerJobPostingsInfo;

    @Builder
    public ReadAdminUserOwnerJobPostingsSummariesResponseDto(CountInfoDto userOwnerJobPostingsInfo) {
        this.userOwnerJobPostingsInfo = userOwnerJobPostingsInfo;

        this.validateSelf();
    }

    public static ReadAdminUserOwnerJobPostingsSummariesResponseDto of(
            Integer count,
            Integer priorPeriodCount,
            Double priorPeriodComparison
    ) {
        return ReadAdminUserOwnerJobPostingsSummariesResponseDto.builder()
                .userOwnerJobPostingsInfo(
                        CountInfoDto.of(
                                count,
                                priorPeriodCount,
                                priorPeriodComparison
                        )
                )
                .build();
    }
}
