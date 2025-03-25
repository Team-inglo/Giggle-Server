package com.inglo.giggle.posting.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.dto.request.AddressRequestDto;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.type.EEmploymentType;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

public record UpdateOwnerJobPostingRequestDto(

        @NotNull(message = "제목은 필수 입력 값입니다.")
        @Size(max = 100, message = "제목은 최대 100자까지 입력 가능합니다.")
        @JsonProperty("title")
        String title,

        @NotNull(message = "직무 카테고리는 필수 입력 값입니다.")
        @JsonProperty("job_category")
        EJobCategory jobCategory,

        @NotNull(message = "근무 시간 정보는 필수 입력 값입니다.")
        @JsonProperty("work_day_times")
        @Valid
        List<WorkDayTimeDto> workDayTimes,

        @NotNull(message = "근무 기간은 필수 입력 값입니다.")
        @JsonProperty("work_period")
        EWorkPeriod workPeriod,

        @NotNull(message = "시급은 필수 입력 값입니다.")
        @Max(value = 2000000000, message = "시급은 2,000,000,000원 이하로 입력 가능합니다.")
        @JsonProperty("hourly_rate")
        Integer hourlyRate,

        @NotNull(message = "고용 형태는 필수 입력 값입니다.")
        @JsonProperty("employment_type")
        EEmploymentType employmentType,

        @NotNull(message = "주소 정보는 필수 입력 값입니다.")
        @JsonProperty("address")
        @Valid
        AddressRequestDto address,

        @JsonProperty("recruitment_dead_line")
        String recruitmentDeadLine,  // null if 상시모집

        @Min(value = 0, message = "모집 인원은 0명 이상으로 입력 가능합니다.")
        @Max(value = 1000, message = "모집 인원은 1000명 이하로 입력 가능합니다.")
        @JsonProperty("recruitment_number")
        Integer recruitmentNumber,

        @NotNull(message = "성별은 필수 입력 값입니다.")
        @JsonProperty("gender")
        EGender gender,

        @Max(value = 100, message = "나이 제한은 100세 이하로 입력 가능합니다.")
        @Min(value = 0, message = "나이 제한은 0세 이상으로 입력 가능합니다.")
        @JsonProperty("age_restriction")
        Integer ageRestriction,

        @NotNull(message = "학력 정보는 필수 입력 값입니다.")
        @JsonProperty("education_level")
        EEducationLevel educationLevel,

        @NotNull(message = "비자 유형은 필수 입력 값입니다.")
        @JsonProperty("visa")
        Set<EVisa> visa,

        @NotNull(message = "담당자 이름은 필수 입력 값입니다.")
        @Size(max = 10, message = "담당자 이름은 최대 10자까지 입력 가능합니다.")
        @JsonProperty("recruiter_name")
        String recruiterName,

        @NotNull(message = "담당자 이메일은 필수 입력 값입니다.")
        @Email(message = "유효한 이메일 형식이 아닙니다.")
        @Size(max = 320, message = "담당자 이메일은 최대 320자까지 입력 가능합니다.")
        @JsonProperty("recruiter_email")
        String recruiterEmail,

        @NotNull(message = "담당자 전화번호는 필수 입력 값입니다.")
        @Size(max = 20, message = "담당자 전화번호는 최대 20자까지 입력 가능합니다.")
        @JsonProperty("recruiter_phone_number")
        String recruiterPhoneNumber,

        @NotNull(message = "설명은 필수 입력 값입니다.")
        @Size(max = 1000, message = "설명은 최대 1000자까지 입력 가능합니다.")
        @JsonProperty("description")
        String description,

        @Size(max = 50, message = "우대 조건은 최대 50자까지 입력 가능합니다.")
        @JsonProperty("preferred_conditions")
        String preferredConditions,

        @JsonProperty("deleted_img_ids")
        List<Long> deletedImgIds
) {
    public record WorkDayTimeDto(
            @NotNull(message = "근무 요일은 필수 입력 값입니다.")
            @JsonProperty("day_of_week")
            EDayOfWeek dayOfWeek,

            @JsonProperty("work_start_time")
            String workStartTime, // should be null if day_of_week is NEGOTIABLE

            @JsonProperty("work_end_time")
            String workEndTime // should be null if day_of_week is NEGOTIABLE
    ) {
    }
}