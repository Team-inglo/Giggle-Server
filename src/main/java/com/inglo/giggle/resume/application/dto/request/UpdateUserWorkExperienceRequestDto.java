package com.inglo.giggle.resume.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateUserWorkExperienceRequestDto(
        @JsonProperty("title")
        @Size(min = 1, max = 20)
        String title,

        @JsonProperty("workplace")
        @Size(min = 1, max = 20)
        String workplace,

        @JsonProperty("start_date")
        LocalDate startDate,

        @JsonProperty("end_date")
        LocalDate endDate,

        @JsonProperty("description")
        @Size(min = 1, max = 200)
        String description
) {
}
