package com.inglo.giggle.resume.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import lombok.Builder;

public class ReadUserWorkExperienceDetailResult extends SelfValidating<ReadUserWorkExperienceDetailResult> {
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
    public ReadUserWorkExperienceDetailResult(
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

    public static ReadUserWorkExperienceDetailResult of(
            String title,
            String workplace,
            String startDate,
            String endDate,
            String description
    ) {
        return ReadUserWorkExperienceDetailResult.builder()
                .title(title)
                .workplace(workplace)
                .startDate(startDate)
                .endDate(endDate)
                .description(description)
                .build();
    }
}
