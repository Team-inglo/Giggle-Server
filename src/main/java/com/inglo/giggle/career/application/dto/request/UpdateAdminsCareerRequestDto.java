package com.inglo.giggle.career.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.career.domain.type.ECareerCategory;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.type.EVisa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;

public record UpdateAdminsCareerRequestDto(

        @NotBlank(message = "title은 필수 입력 값입니다.")
        @JsonProperty("title")
        String title,

        @JsonProperty("career_category")
        ECareerCategory careerCategory,

        @JsonProperty("host_name")
        String hostName,

        @JsonProperty("organizer_name")
        String organizerName,

        @JsonProperty("address")
        String address,

        @JsonProperty("recruitment_start_date")
        String recruitmentStartDate, // ISO 8601 형식: "yyyy-MM-dd"

        @JsonProperty("recruitment_end_date")
        String recruitmentEndDate,

        @JsonProperty("reward")
        Integer reward,

        @JsonProperty("visa")
        Set<EVisa> visa,

        @JsonProperty("recruitment_number")
        Integer recruitmentNumber,

        @NotNull
        @JsonProperty("education")
        EEducationLevel education,

        @JsonProperty("preferred_conditions")
        String preferredConditions,

        @JsonProperty("details")
        String details,

        @NotBlank
        @JsonProperty("application_url")
        String applicationUrl,

        @JsonProperty("delete_img_ids")
        List<Long> deleteImgIds

) {
}
