package com.inglo.giggle.resume.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record UpdateUserEducationRequestDto(
        @JsonProperty("education_level")
        String educationLevel,

        @JsonProperty("school_id")
        Long schoolId,

        @JsonProperty("major")
        String major,

        @JsonProperty("gpa")
        Double gpa,

        @JsonProperty("start_date")
        LocalDate startDate,

        @JsonProperty("end_date")
        LocalDate endDate,

        @JsonProperty("grade")
        Integer grade
) {
}
