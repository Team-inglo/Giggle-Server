package com.inglo.giggle.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CountInfoDto extends SelfValidating<CountInfoDto> {

    @NotNull
    @JsonProperty("count")
    private final Integer count;

    @NotNull
    @JsonProperty("prior_period_count")
    private final Integer priorPeriodCount;

    @NotNull
    @JsonProperty("prior_period_comparison")
    private final Double priorPeriodComparison;

    @Builder
    public CountInfoDto(
            Integer count,
            Integer priorPeriodCount,
            Double priorPeriodComparison
    ) {
        this.count = count;
        this.priorPeriodCount = priorPeriodCount;
        this.priorPeriodComparison = priorPeriodComparison;
        this.validateSelf();
    }

    public static CountInfoDto of(
            Integer count,
            Integer priorPeriodCount,
            Double priorPeriodComparison
    ) {
        return CountInfoDto.builder()
                .count(count)
                .priorPeriodCount(priorPeriodCount)
                .priorPeriodComparison(priorPeriodComparison)
                .build();
    }
}
