package com.inglo.giggle.resume.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record CreateUserWorkExperienceRequestDto(
        @JsonProperty("title")
        String title,

        @JsonProperty("workplace")
        String workplace,

        @JsonProperty("start_date")
        LocalDate startDate,

        @JsonProperty("end_date")
        LocalDate endDate,

        @JsonProperty("description")
        String description
) {
}
