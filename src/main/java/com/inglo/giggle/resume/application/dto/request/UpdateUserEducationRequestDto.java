package com.inglo.giggle.resume.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateUserEducationRequestDto(
        @NotNull(message = "education_level은 필수 입력 값입니다.")
        @JsonProperty("education_level")
        String educationLevel,

        @NotNull(message = "school_id은 필수 입력 값입니다.")
        @JsonProperty("school_id")
        Long schoolId,

        @NotNull(message = "major은 필수 입력 값입니다.")
        @JsonProperty("major")
        @Size(min = 1, max = 30, message = "전공은 1자 이상 30자 이하로 입력 가능합니다.")
        String major,

        @NotNull(message = "gpa는 필수 입력 값입니다.")
        @Max(value = 5, message = "gpa는 5 이하로 입력 가능합니다.")
        @Min(value = 0, message = "gpa는 0 이상으로 입력 가능합니다.")
        @JsonProperty("gpa")
        Double gpa,

        @NotNull(message = "start_date은 필수 입력 값입니다.")
        @JsonProperty("start_date")
        LocalDate startDate,

        @NotNull(message = "end_date은 필수 입력 값입니다.")
        @JsonProperty("end_date")
        LocalDate endDate,

        @NotNull(message = "grade은 필수 입력 값입니다.")
        @Max(value = 6, message = "grade은 6 이하로 입력 가능합니다.")
        @Min(value = 1, message = "grade은 1 이상으로 입력 가능합니다.")
        @JsonProperty("grade")
        Integer grade
) {
}
