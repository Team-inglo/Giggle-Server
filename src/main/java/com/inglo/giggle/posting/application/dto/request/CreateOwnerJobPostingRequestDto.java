package com.inglo.giggle.posting.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.type.EEmploymentType;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateOwnerJobPostingRequestDto(

        @NotNull
        @Size(max = 100)
        @JsonProperty("title")
        String title,

        @NotNull
        @JsonProperty("job_category")
        EJobCategory jobCategory,

        @NotNull
        @JsonProperty("work_day_times")
        List<WorkDayTimeDto> workDayTimes,

        @NotNull
        @JsonProperty("work_period")
        EWorkPeriod workPeriod,

        @NotNull
        @JsonProperty("hourly_rate")
        Integer hourlyRate,

        @NotNull
        @JsonProperty("employment_type")
        EEmploymentType employmentType,

        @NotNull
        @JsonProperty("address")
        AddressDto address,

        @JsonProperty("recruitment_dead_line")
        String recruitmentDeadLine,  // null if 상시모집

        @JsonProperty("recruitment_number")
        Integer recruitmentNumber,

        @NotNull
        @JsonProperty("gender")
        EGender gender,

        @JsonProperty("age_restriction")
        Integer ageRestriction,

        @NotNull
        @JsonProperty("education_level")
        EEducationLevel educationLevel,

        @NotNull
        @JsonProperty("visa")
        EVisa visa,

        @NotNull
        @Size(max = 10)
        @JsonProperty("recruiter_name")
        String recruiterName,

        @NotNull
        @Email
        @Size(max = 320)
        @JsonProperty("recruiter_email")
        String recruiterEmail,

        @NotNull
        @Size(max = 20)
        @JsonProperty("recruiter_phone_number")
        String recruiterPhoneNumber,

        @NotNull
        @Size(max = 1000)
        @JsonProperty("description")
        String description,

        @Size(max = 50)
        @JsonProperty("preferred_conditions")
        String preferredConditions
) {
    public record WorkDayTimeDto(
            @NotNull
            @JsonProperty("day_of_week")
            EDayOfWeek dayOfWeek,

            @JsonProperty("work_start_time")
            String workStartTime, // should be null if day_of_week is NEGOTIABLE

            @JsonProperty("work_end_time")
            String workEndTime // should be null if day_of_week is NEGOTIABLE
    ) {
    }

    public record AddressDto(
            @NotNull
            @JsonProperty("address_name")
            String addressName,

            @NotNull
            @JsonProperty("region_1depth_name")
            String region1DepthName,

            @NotNull
            @JsonProperty("region_2depth_name")
            String region2DepthName,

            @NotNull
            @JsonProperty("region_3depth_name")
            String region3DepthName,

            @JsonProperty("region_4depth_name")
            String region4DepthName,

            @NotNull
            @JsonProperty("address_detail")
            String addressDetail,

            @NotNull
            @JsonProperty("longitude")
            Double longitude,

            @NotNull
            @JsonProperty("latitude")
            Double latitude
    ) {
    }

}
