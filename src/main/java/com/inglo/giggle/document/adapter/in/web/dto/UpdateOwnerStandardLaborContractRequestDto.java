package com.inglo.giggle.document.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.AddressRequestDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public record UpdateOwnerStandardLaborContractRequestDto(

        @JsonProperty("company_name")
        String companyName,

        @JsonProperty("company_registration_number")
        String companyRegistrationNumber,

        @JsonProperty("phone_number")
        String phoneNumber,

        @JsonProperty("name")
        String name,

        @JsonProperty("start_date")
        LocalDate startDate,

        @JsonProperty("end_date")
        LocalDate endDate,

        @JsonProperty("address")
        AddressRequestDto address,

        @JsonProperty("description")
        String description,

        @JsonProperty("work_day_time_list")
        List<WorkDayTime> workDayTimeList,

        @JsonProperty("weekly_last_days")
        Set<String> weeklyLastDays,

        @JsonProperty("hourly_rate")
        Integer hourlyRate,

        @JsonProperty("bonus")
        Integer bonus,

        @JsonProperty("additional_salary")
        Integer additionalSalary,

        @JsonProperty("wage_rate")
        Double wageRate,

        @JsonProperty("payment_day")
        Integer paymentDay,

        @JsonProperty("payment_method")
        String paymentMethod,

        @JsonProperty("insurance")
        Set<String> insurance,

        @JsonProperty("signature_base64")
        String signatureBase64
) {

    public record WorkDayTime(
            @JsonProperty("day_of_week")
            String dayOfWeek,

            @JsonProperty("work_start_time")
            LocalTime workStartTime,

            @JsonProperty("work_end_time")
            LocalTime workEndTime,

            @JsonProperty("break_start_time")
            LocalTime breakStartTime,

            @JsonProperty("break_end_time")
            LocalTime breakEndTime
    ) {}
}
