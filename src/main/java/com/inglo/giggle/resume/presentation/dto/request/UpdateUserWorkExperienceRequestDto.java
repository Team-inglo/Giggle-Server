package com.inglo.giggle.resume.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateUserWorkExperienceRequestDto(
        @JsonProperty("title")
        @NotNull(message = "title은 필수 입력 값입니다.")
        @Size(min = 1, max = 20, message = "title은 1자 이상 20자 이하로 입력 가능합니다.")
        String title,

        @JsonProperty("workplace")
        @NotNull(message = "workplace은 필수 입력 값입니다.")
        @Size(min = 1, max = 20, message = "workplace은 1자 이상 20자 이하로 입력 가능합니다.")
        String workplace,

        @NotNull(message = "start_date은 필수 입력 값입니다.")
        @JsonProperty("start_date")
        LocalDate startDate,

        @JsonProperty("end_date")
        LocalDate endDate,

        @JsonProperty("description")
        @Size(min = 1, max = 200, message = "description은 1자 이상 200자 이하로 입력 가능합니다.")
        String description
) {
    public UpdateUserWorkExperienceRequestDto validate() {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new CommonException(ErrorCode.INVALID_DATE_RANGE);
        }

        return this;
    }
}
