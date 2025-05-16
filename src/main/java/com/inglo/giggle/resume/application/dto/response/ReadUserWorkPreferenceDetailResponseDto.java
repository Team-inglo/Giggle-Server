package com.inglo.giggle.resume.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.resume.domain.PreferenceAddress;
import com.inglo.giggle.resume.domain.WorkPreference;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadUserWorkPreferenceDetailResponseDto extends SelfValidating<ReadUserWorkPreferenceDetailResponseDto> {
    @JsonProperty("job_categories")
    private final List<String> jobCategory;

    @JsonProperty("employment_types")
    private final List<String> employmentType;

    @JsonProperty("preference_addresses")
    private final List<PreferenceAddressDto> preferenceAddresses;

    @Builder
    public ReadUserWorkPreferenceDetailResponseDto(
            List<String> jobCategory,
            List<String> employmentType,
            List<PreferenceAddressDto> preferenceAddresses
    ) {
        this.jobCategory = jobCategory;
        this.employmentType = employmentType;
        this.preferenceAddresses = preferenceAddresses;

        this.validateSelf();
    }

    public static ReadUserWorkPreferenceDetailResponseDto fromEntity(
            WorkPreference workPreference
    ) {
        return ReadUserWorkPreferenceDetailResponseDto.builder()
                .jobCategory(workPreference.getJobCategories().stream()
                        .map(EJobCategory::name)
                        .toList())
                .employmentType(workPreference.getEmploymentTypes().stream()
                        .map(Enum::name)
                        .toList())
                .preferenceAddresses(workPreference.getPreferenceAddresses().stream()
                        .map(PreferenceAddressDto::fromEntity)
                        .toList())
                .build();
    }

    @Getter
    public static class PreferenceAddressDto extends SelfValidating<PreferenceAddressDto> {
        @JsonProperty("region_1depth_name")
        private final String region1DepthName;

        @JsonProperty("region_2depth_name")
        private final String region2DepthName;

        @JsonProperty("region_3depth_name")
        private final String region3DepthName;

        @JsonProperty("region_4depth_name")
        private final String region4DepthName;

        @Builder
        public PreferenceAddressDto(String region1DepthName, String region2DepthName, String region3DepthName, String region4DepthName) {
            this.region1DepthName = region1DepthName;
            this.region2DepthName = region2DepthName;
            this.region3DepthName = region3DepthName;
            this.region4DepthName = region4DepthName;

            this.validateSelf();
        }

        public static PreferenceAddressDto fromEntity(PreferenceAddress preferenceAddress) {
            return PreferenceAddressDto.builder()
                    .region1DepthName(preferenceAddress.getRegion1DepthName())
                    .region2DepthName(preferenceAddress.getRegion2DepthName())
                    .region3DepthName(preferenceAddress.getRegion3DepthName())
                    .region4DepthName(preferenceAddress.getRegion4DepthName())
                    .build();
        }
    }
}
