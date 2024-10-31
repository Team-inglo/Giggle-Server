package com.inglo.giggle.resume.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.resume.domain.WorkExperience;
import lombok.Builder;

public class ReadUserWorkExperienceDetailResponseDto extends SelfValidating<ReadUserWorkExperienceDetailResponseDto> {
    @JsonProperty("title")
    private final String title;

    @JsonProperty("workplace")
    private final String workplace;

    @JsonProperty("start_date")
    private final String startDate;

    @JsonProperty("end_date")
    private final String endDate;

    @JsonProperty("description")
    private final String description;

    @Builder
    public ReadUserWorkExperienceDetailResponseDto(
            String title,
            String workplace,
            String startDate,
            String endDate,
            String description
    ) {
        this.title = title;
        this.workplace = workplace;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;

        this.validateSelf();
    }

    public static ReadUserWorkExperienceDetailResponseDto fromEntity(WorkExperience workExperience) {
        return ReadUserWorkExperienceDetailResponseDto.builder()
                .title(workExperience.getExperienceTitle())
                .workplace(workExperience.getWorkplace())
                .startDate(workExperience.getStartDate().toString())
                .endDate(workExperience.getEndDate() != null ? workExperience.getEndDate().toString() : null)
                .description(workExperience.getDescription())
                .build();
    }
}
