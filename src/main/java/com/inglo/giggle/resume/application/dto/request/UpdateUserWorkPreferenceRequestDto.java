package com.inglo.giggle.resume.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.posting.domain.type.EEmploymentType;
import com.inglo.giggle.posting.domain.type.EJobCategory;

import java.util.Set;

public record UpdateUserWorkPreferenceRequestDto(
        @JsonProperty("job_categories")
        Set<EJobCategory> jobCategories,

        @JsonProperty("employment_types")
        Set<EEmploymentType> employmentTypes,

        @JsonProperty("preference_addresses")
        Set<PreferenceAddressDto> preferenceAddresses
) {
    public record PreferenceAddressDto(
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
}
