package com.inglo.giggle.document.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.dto.request.AddressRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public record UpdateOwnerStandardLaborContractRequestDto(

        @NotNull(message = "회사/점포명을 입력해주세요.")
        @Size(min = 1, max = 150, message = "회사/점포명은 150자 이하로 입력해주세요.")
        @JsonProperty("company_name")
        String companyName,

        @NotNull(message = "사업자 등록번호를 입력해주세요.")
        @Size(min = 1, max = 12, message = "사업자 등록번호는 12자 이하로 입력해주세요.")
        @JsonProperty("company_registration_number")
        String companyRegistrationNumber,

        @NotNull(message = "전화번호를 입력해주세요.")
        @Size(min = 10, max = 20, message = "전화번호는 10자 이상 20자 이하로 입력해주세요.")
        @JsonProperty("phone_number")
        String phoneNumber,

        @NotNull(message = "이름을 입력해주세요.")
        @Size(min = 1, max = 150, message = "이름은 150자 이하로 입력해주세요.")
        @JsonProperty("name")
        String name,

        @NotNull(message = "근무 시작일을 입력해주세요.")
        @JsonProperty("start_date")
        LocalDate startDate,

        @NotNull(message = "근무 종료일을 입력해주세요.")
        @JsonProperty("end_date")
        LocalDate endDate,

        @NotNull(message = "주소를 입력해주세요.")
        @JsonProperty("address")
        @Valid
        AddressRequestDto address,

        @NotBlank(message = "근무 상세 정보를 입력해주세요.")
        @Size(min = 1, max = 200, message = "근무 상세 정보는 1자 이상 200자 이하로 입력해주세요.")
        @JsonProperty("description")
        String description,

        @NotNull(message = "근무 시간을 입력해주세요.")
        @JsonProperty("work_day_time_list")
        @Valid
        List<WorkDayTime> workDayTimeList,

        @NotNull(message = "주휴일을 입력해주세요.")
        @JsonProperty("weekly_last_days")
        Set<String> weeklyLastDays,

        @NotNull(message = "시급을 입력해주세요.")
        @Max(value = 2000000000, message = "시급은 2,000,000,000원 이하로 입력해주세요.")
        @JsonProperty("hourly_rate")
        Integer hourlyRate,

        @Max(value = 2000000000, message = "성과금은 2,000,000,000원 이하로 입력해주세요.")
        @JsonProperty("bonus")
        Integer bonus,

        @Max(value = 2000000000, message = "추가급여는 2,000,000,000원 이하로 입력해주세요.")
        @JsonProperty("additional_salary")
        Integer additionalSalary,

        @NotNull(message = "wage rate룰 입력해주세요.")
        @JsonProperty("wage_rate")
        Double wageRate,

        @NotNull(message = "지급일을 입력해주세요.")
        @Max(value = 31, message = "지급일은 31일 이하로 입력해주세요.")
        @Min(value = 1, message = "지급일은 1일 이상으로 입력해주세요.")
        @JsonProperty("payment_day")
        Integer paymentDay,

        @NotNull(message = "지급 방법을 입력해주세요.")
        @JsonProperty("payment_method")
        String paymentMethod,

        @NotNull(message = "보험을 입력해주세요.")
        @JsonProperty("insurance")
        Set<String> insurance,

        @NotNull(message = "서명을 입력해주세요.")
        @JsonProperty("signature_base64")
        String signatureBase64

) {

    public record WorkDayTime(
            @NotNull
            @JsonProperty("day_of_week")
            String dayOfWeek,

            @NotNull
            @JsonProperty("work_start_time")
            LocalTime workStartTime,

            @NotNull
            @JsonProperty("work_end_time")
            LocalTime workEndTime,

            @JsonProperty("break_start_time")
            LocalTime breakStartTime,

            @JsonProperty("break_end_time")
            LocalTime breakEndTime
    ) {}
}
