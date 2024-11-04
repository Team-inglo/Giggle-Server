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
import lombok.Getter;

import java.util.List;

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
        List<WorkDayTimeDto> workDayTimes,

        @NotNull(message = "근무 기간은 필수 입력 값입니다.")
        @JsonProperty("work_period")
        EWorkPeriod workPeriod,

        @NotNull(message = "시급은 필수 입력 값입니다.")
        @JsonProperty("hourly_rate")
        Integer hourlyRate,

        @NotNull(message = "고용 형태는 필수 입력 값입니다.")
        @JsonProperty("employment_type")
        EEmploymentType employmentType,

        @NotNull(message = "주소 정보는 필수 입력 값입니다.")
        @JsonProperty("address")
        AddressDto address,

        @JsonProperty("recruitment_dead_line")
        String recruitmentDeadLine,  // null if 상시모집

        @JsonProperty("recruitment_number")
        Integer recruitmentNumber,

        @NotNull(message = "성별은 필수 입력 값입니다.")
        @JsonProperty("gender")
        EGender gender,

        @JsonProperty("age_restriction")
        Integer ageRestriction,

        @NotNull(message = "학력 정보는 필수 입력 값입니다.")
        @JsonProperty("education_level")
        EEducationLevel educationLevel,

        @NotNull(message = "비자 유형은 필수 입력 값입니다.")
        @JsonProperty("visa")
        EVisa visa,

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

    public record AddressDto(
            @NotNull(message = "주소명은 필수 입력 값입니다.")
            @JsonProperty("address_name")
            String addressName,

            @NotNull(message = "1차 지역명은 필수 입력 값입니다.")
            @JsonProperty("region_1depth_name")
            String region1DepthName,

            @NotNull(message = "2차 지역명은 필수 입력 값입니다.")
            @JsonProperty("region_2depth_name")
            String region2DepthName,

            @NotNull(message = "3차 지역명은 필수 입력 값입니다.")
            @JsonProperty("region_3depth_name")
            String region3DepthName,

            @JsonProperty("region_4depth_name")
            String region4DepthName,

            @NotNull(message = "상세 주소는 필수 입력 값입니다.")
            @JsonProperty("address_detail")
            String addressDetail,

            @NotNull(message = "경도는 필수 입력 값입니다.")
            @JsonProperty("longitude")
            Double longitude,

            @NotNull(message = "위도는 필수 입력 값입니다.")
            @JsonProperty("latitude")
            Double latitude
    ) {
    }
}