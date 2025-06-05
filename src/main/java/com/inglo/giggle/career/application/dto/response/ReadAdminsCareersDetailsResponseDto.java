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
public class ReadAdminsCareersDetailsResponseDto extends SelfValidating<ReadAdminsCareersDetailsResponseDto> {

    @JsonProperty("imgs")
    private final List<imgs> imgs;

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
    public ReadAdminsCareersDetailsResponseDto(
            List<imgs> imgs,
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
        this.imgs = imgs;
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

    @Getter
    public static class imgs extends SelfValidating<imgs> {

        @JsonProperty("id")
        @NotNull(message = "이미지 ID는 필수입니다.")
        private final Long id;

        @JsonProperty("urls")
        @NotNull(message = "이미지 URL은 필수입니다.")
        private final String url;

        @Builder
        public imgs(Long id, String url) {
            this.id = id;
            this.url = url;
        }
    }
}
