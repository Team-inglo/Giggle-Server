package com.inglo.giggle.posting.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.type.EEmploymentType;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import jakarta.validation.constraints.*;

import java.util.List;

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
        AddressDto address,

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
        EVisa visa,

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

    public record AddressDto(
            @NotNull
            @JsonProperty("address_name")
            String addressName,

            @NotNull
            @JsonProperty("region_1depth_name")
            String region1DepthName,

            @NotNull
            @JsonProperty("region_2depth_name")
            String region2DepthName,

            @NotNull
            @JsonProperty("region_3depth_name")
            String region3DepthName,

            @JsonProperty("region_4depth_name")
            String region4DepthName,

            @NotNull(message = "상세주소를 입력해주세요.")
            @Size(max = 100, message = "상세주소는 100자 이하로 입력해주세요.")
            @JsonProperty("address_detail")
            String addressDetail,

            @NotNull
            @JsonProperty("longitude")
            Double longitude,

            @NotNull
            @JsonProperty("latitude")
            Double latitude
    ) {
    }

}
