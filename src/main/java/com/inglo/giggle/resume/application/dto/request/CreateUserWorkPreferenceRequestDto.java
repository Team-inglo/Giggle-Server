package com.inglo.giggle.resume.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.posting.domain.type.EEmploymentType;
import com.inglo.giggle.posting.domain.type.EJobCategory;

public record CreateUserWorkPreferenceRequestDto(
        @JsonProperty("job_category")
        EJobCategory jobCategory,

        @JsonProperty("employment_type")
        EEmploymentType employmentType,

        @JsonProperty("region_1depth_name")
        String region1DepthName,

        @JsonProperty("region_2depth_name")
        String region2DepthName,

        @JsonProperty("region_3depth_name")
        String region3DepthName,

        @JsonProperty("region_4depth_name")
        String region4DepthName
) {
}
