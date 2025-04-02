package com.inglo.giggle.posting.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.CountInfoDto;
import com.inglo.giggle.core.dto.SelfValidating;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadAdminUserOwnerJobPostingsApplicationSuccessSummariesResponseDto extends SelfValidating<ReadAdminUserOwnerJobPostingsApplicationSuccessSummariesResponseDto> {

    @JsonProperty("successful_matches_info")
    private final CountInfoDto successfulMatchesInfo;

    @Builder
    public ReadAdminUserOwnerJobPostingsApplicationSuccessSummariesResponseDto(
            CountInfoDto successfulMatchesInfo
    ) {
        this.successfulMatchesInfo = successfulMatchesInfo;

        this.validateSelf();
    }

    public static ReadAdminUserOwnerJobPostingsApplicationSuccessSummariesResponseDto of(
            Integer count,
            Integer priorPeriodCount,
            Double priorPeriodComparison
    ) {
        return ReadAdminUserOwnerJobPostingsApplicationSuccessSummariesResponseDto.builder()
                .successfulMatchesInfo(
                        CountInfoDto.of(
                                count,
                                priorPeriodCount,
                                priorPeriodComparison
                        )
                )
                .build();
    }
}
