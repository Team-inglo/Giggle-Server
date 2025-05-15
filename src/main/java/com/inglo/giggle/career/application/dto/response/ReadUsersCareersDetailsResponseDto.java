package com.inglo.giggle.career.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.career.domain.type.ECareerCategory;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.type.EVisa;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
public class ReadUsersCareersDetailsResponseDto extends SelfValidating<ReadUsersCareersDetailsResponseDto> {

    @JsonProperty("img_urls")
    private final List<String> imgUrls; // null 허용

    @JsonProperty("is_book_marked")
    private final Boolean isBookMarked; // null 허용

    @JsonProperty("title")
    private final String title;

    @JsonProperty("career_category")
    private final ECareerCategory careerCategory; // nullable

    @JsonProperty("host_name")
    private final String hostName;

    @JsonProperty("organizer_name")
    private final String organizerName;

    @JsonProperty("address")
    private final String address;

    @JsonProperty("recruitment_start_date")
    private final String recruitmentStartDate;

    @JsonProperty("recruitment_end_date")
    private final String recruitmentEndDate;

    @JsonProperty("reward")
    private final Integer reward;

    @JsonProperty("visa")
    private final Set<EVisa> visa;

    @JsonProperty("recruitment_number")
    private final Integer recruitmentNumber;

    @JsonProperty("education")
    private final EEducationLevel education;

    @JsonProperty("preferred_conditions")
    private final String preferredConditions;

    @JsonProperty("details")
    private final String details;

    @NotNull
    @JsonProperty("application_url")
    private final String applicationUrl;

    @Builder
    public ReadUsersCareersDetailsResponseDto(
            List<String> imgUrls,
            Boolean isBookMarked,
            String title,
            ECareerCategory careerCategory,
            String hostName,
            String organizerName,
            String address,
            String recruitmentStartDate,
            String recruitmentEndDate,
            Integer reward,
            Set<EVisa> visa,
            Integer recruitmentNumber,
            EEducationLevel education,
            String preferredConditions,
            String details,
            String applicationUrl
    ) {
        this.imgUrls = imgUrls;
        this.isBookMarked = isBookMarked;
        this.title = title;
        this.careerCategory = careerCategory;
        this.hostName = hostName;
        this.organizerName = organizerName;
        this.address = address;
        this.recruitmentStartDate = recruitmentStartDate;
        this.recruitmentEndDate = recruitmentEndDate;
        this.reward = reward;
        this.visa = visa;
        this.recruitmentNumber = recruitmentNumber;
        this.education = education;
        this.preferredConditions = preferredConditions;
        this.details = details;
        this.applicationUrl = applicationUrl;
        this.validateSelf();
    }
}
