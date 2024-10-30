package com.inglo.giggle.resume.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateUserEducationRequestDto(
    @JsonProperty("education_level")
    String educationLevel,

    @JsonProperty("school_id")
    Long schoolId,

    @JsonProperty("major")
    @Size(min = 1, max = 30)
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
