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

public record CreateOwnerJobPostingRequestDto(

        @NotNull(message = "제목을 입력해주세요.")
        @Size(max = 100, message = "제목은 100자 이하로 입력해주세요.")
        @JsonProperty("title")
        String title,

        @NotNull(message = "직종을 선택해주세요.")
        @JsonProperty("job_category")
        EJobCategory jobCategory,

        @NotNull(message = "근무시간을 선택해주세요.")
        @JsonProperty("work_day_times")
        @Valid
        List<WorkDayTimeDto> workDayTimes,

        @NotNull(message = "근무기간을 선택해주세요.")
        @JsonProperty("work_period")
        EWorkPeriod workPeriod,

        @NotNull(message = "시급을 입력해주세요.")
        @Max(value = 1000000, message = "시급은 1,000,000원 이하로 입력해주세요.")
        @Min(value = 10030, message = "시급은 10030원 이상으로 입력해주세요.")
        @JsonProperty("hourly_rate")
        Integer hourlyRate,

        @NotNull(message = "고용형태를 선택해주세요.")
        @JsonProperty("employment_type")
        EEmploymentType employmentType,

        @NotNull(message = "근무지 주소를 입력해주세요.")
        @JsonProperty("address")
        @Valid
        AddressRequestDto address,

        @JsonProperty("recruitment_dead_line")
        String recruitmentDeadLine,  // null if 상시모집

        @Max(value = 1000, message = "모집인원은 1000명 이하로 입력해주세요.")
        @Min(value = 0, message = "모집인원은 0명 이상으로 입력해주세요.")
        @JsonProperty("recruitment_number")
        Integer recruitmentNumber,

        @NotNull(message = "성별을 선택해주세요.")
        @JsonProperty("gender")
        EGender gender,

        @Max(value = 100, message = "나이제한은 100세 이하로 입력해주세요.")
        @Min(value = 0, message = "나이제한은 0세 이상으로 입력해주세요.")
        @JsonProperty("age_restriction")
        Integer ageRestriction,

        @NotNull(message = "학력을 선택해주세요.")
        @JsonProperty("education_level")
        EEducationLevel educationLevel,

        @NotNull(message = "비자를 입력해주세요.")
        @JsonProperty("visa")
        Set<EVisa> visa,

        @NotNull(message = "담당자명을 입력해주세요.")
        @Size(max = 10, message = "담당자명은 10자 이하로 입력해주세요.")
        @JsonProperty("recruiter_name")
        String recruiterName,

        @NotNull(message = "담당자 이메일을 입력해주세요.")
        @Email(message = "이메일 형식에 맞게 입력해주세요.")
        @Size(max = 320, message = "이메일은 320자 이하로 입력해주세요.")
        @JsonProperty("recruiter_email")
        String recruiterEmail,

        @NotNull(message = "담당자 전화번호를 입력해주세요.")
        @Size(min = 8, max = 20, message = "전화번호는 8자 이상 20자 이하로 입력해주세요.")
        @JsonProperty("recruiter_phone_number")
        String recruiterPhoneNumber,

        @NotNull(message = "공고 설명을 입력해주세요.")
        @Size(max = 1000, message = "공고 설명은 1000자 이하로 입력해주세요.")
        @JsonProperty("description")
        String description,

        @Size(max = 50, message = "우대사항은 50자 이하로 입력해주세요.")
        @JsonProperty("preferred_conditions")
        String preferredConditions
) {
    public record WorkDayTimeDto(
            @NotNull(message = "요일을 선택해주세요.")
            @JsonProperty("day_of_week")
            EDayOfWeek dayOfWeek,

            @JsonProperty("work_start_time")
            String workStartTime, // should be null if day_of_week is NEGOTIABLE

            @JsonProperty("work_end_time")
            String workEndTime // should be null if day_of_week is NEGOTIABLE
    ) {
    }
}
