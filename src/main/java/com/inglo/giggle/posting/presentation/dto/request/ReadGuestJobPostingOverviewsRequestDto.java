package com.inglo.giggle.posting.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReadGuestJobPostingOverviewsRequestDto(

        @NotNull(message = "페이지 번호를 입력해주세요.")
        @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
        @JsonProperty("page")
        Integer page,

        @NotNull(message = "페이지 크기를 입력해주세요.")
        @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
        @JsonProperty("size")
        Integer size,

        @JsonProperty("search")
        String search,

        @JsonProperty("sorting")
        String sorting,

        @JsonProperty("region1Depth")
        String region1Depth,

        @JsonProperty("region2Depth")
        String region2Depth,

        @JsonValue()
        String region3Depth,

        @JsonProperty("industry")
        String industry,

        @JsonProperty("work_period")
        String workPeriod,

        @JsonProperty("work_days_per_week")
        String workDaysPerWeek,

        @JsonProperty("working_day")
        String workingDay,

        @JsonProperty("working_hours")
        String workingHours,

        @JsonProperty("recruitment_period")
        String recruitmentPeriod,

        @JsonProperty("employment_type")
        String employmentType,

        @JsonProperty("visa")
        String visa,

        @JsonProperty("type")
        String type
) {
}
