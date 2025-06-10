package com.inglo.giggle.career.application.dto.response;

import com.inglo.giggle.career.domain.type.ERecruitmentStatus;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.career.domain.type.ECareerCategory;
import com.inglo.giggle.core.type.EVisa;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadUsersCareersOverviewsResponseDto extends SelfValidating<ReadUsersCareersOverviewsResponseDto> {

    @NotNull
    @JsonProperty("career_list")
    private final List<CareerOverviewDto> careerList;

    @NotNull
    @JsonProperty("has_next")
    private final Boolean hasNext;

    @Builder
    public ReadUsersCareersOverviewsResponseDto(List<CareerOverviewDto> careerList, Boolean hasNext) {
        this.careerList = careerList;
        this.hasNext = hasNext;
        this.validateSelf();
    }

    @Getter
    public static class CareerOverviewDto {

        @NotNull
        @JsonProperty("id")
        private final Long id;

        @JsonProperty("is_book_marked")
        private final Boolean isBookMarked;

        @NotNull
        @JsonProperty("title")
        private final String title;

        @NotNull
        @JsonProperty("career_category")
        private final ECareerCategory careerCategory;

        @NotNull
        @JsonProperty("visa")
        private final List<EVisa> visa;

        @NotNull
        @JsonProperty("host_name")
        private final String hostName;

        @NotNull
        @JsonProperty("organizer_name")
        private final String organizerName;

        @NotNull
        @JsonProperty("left_days")
        private final int leftDays;

        @NotNull
        @JsonProperty("status")
        private final ERecruitmentStatus status;

        @NotNull
        @JsonProperty("recruitment_start_date")
        private final String recruitmentStartDate;

        @NotNull
        @JsonProperty("recruitment_end_date")
        private final String recruitmentEndDate;

        @NotNull
        @JsonProperty("created_at")
        private final String createdAt;

        @Builder
        public CareerOverviewDto(
                Long id,
                Boolean isBookMarked,
                String title,
                ECareerCategory careerCategory,
                List<EVisa> visa,
                String hostName,
                String organizerName,
                int leftDays,
                ERecruitmentStatus status,
                String recruitmentStartDate,
                String recruitmentEndDate,
                String createdAt
        ) {
            this.id = id;
            this.isBookMarked = isBookMarked;
            this.title = title;
            this.careerCategory = careerCategory;
            this.visa = visa;
            this.hostName = hostName;
            this.organizerName = organizerName;
            this.leftDays = leftDays;
            this.status = status;
            this.recruitmentStartDate = recruitmentStartDate;
            this.recruitmentEndDate = recruitmentEndDate;
            this.createdAt = createdAt;
        }
    }
}
