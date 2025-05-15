package com.inglo.giggle.resume.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.resume.domain.WorkPreference;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadUserWorkPreferenceDetailResponseDto {
    @JsonProperty("id")
    private final Long id;

    @JsonProperty("job_category")
    private final String jobCategory;

    @JsonProperty("employment_type")
    private final String employmentType;

    @JsonProperty("region_1depth_name")
    private final String region1DepthName;

    @JsonProperty("region_2depth_name")
    private final String region2DepthName;

    @JsonProperty("region_3depth_name")
    private final String region3DepthName;

    @JsonProperty("region_4depth_name")
    private final String region4DepthName;

    @Builder
    public ReadUserWorkPreferenceDetailResponseDto(
            Long id,
            String jobCategory,
            String employmentType,
            String region1DepthName,
            String region2DepthName,
            String region3DepthName,
            String region4DepthName
    ) {
        this.id = id;
        this.jobCategory = jobCategory;
        this.employmentType = employmentType;
        this.region1DepthName = region1DepthName;
        this.region2DepthName = region2DepthName;
        this.region3DepthName = region3DepthName;
        this.region4DepthName = region4DepthName;
    }

    public static ReadUserWorkPreferenceDetailResponseDto fromEntity(
            WorkPreference workPreference
    ) {
        return ReadUserWorkPreferenceDetailResponseDto.builder()
                .id(workPreference.getId())
                .jobCategory(workPreference.getJobCategory() != null ? workPreference.getJobCategory().toString() : null)
                .employmentType(workPreference.getEmploymentType() != null ? workPreference.getEmploymentType().toString() : null)
                .region1DepthName(workPreference.getRegion1DepthName())
                .region2DepthName(workPreference.getRegion2DepthName())
                .region3DepthName(workPreference.getRegion3DepthName())
                .region4DepthName(workPreference.getRegion4DepthName())
                .build();
    }
}
