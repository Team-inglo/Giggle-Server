package com.inglo.giggle.document.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.dto.request.AddressRequestDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public record UpdateOwnerStandardLaborContractRequestDto(

        @NotNull
        @Size(min = 1, max = 150)
        @JsonProperty("company_name")
        String companyName,

        @JsonProperty("company_registration_number")
        String companyRegistrationNumber,

        @NotNull
        @Size(min = 10, max = 20)
        @JsonProperty("phone_number")
        String phoneNumber,

        @NotNull
        @Size(min = 1, max = 150)
        @JsonProperty("name")
        String name,

        @NotNull
        @JsonProperty("start_date")
        LocalDate startDate,

        @NotNull
        @JsonProperty("end_date")
        LocalDate endDate,

        @NotNull
        @JsonProperty("address")
        AddressRequestDto address,

        @NotNull
        @Size(max = 200)
        @JsonProperty("description")
        String description,

        @NotNull
        @JsonProperty("work_day_time_list")
        List<WorkDayTime> workDayTimeList,

        @NotNull
        @JsonProperty("weekly_last_days")
        Set<String> weeklyLastDays,

        @NotNull
        @JsonProperty("hourly_rate")
        Integer hourlyRate,

        @JsonProperty("bonus")
        Integer bonus,

        @JsonProperty("additional_salary")
        Integer additionalSalary,

        @NotNull
        @JsonProperty("wage_rate")
        Double wageRate,

        @NotNull
        @JsonProperty("payment_day")
        Integer paymentDay,

        @NotNull
        @JsonProperty("payment_method")
        String paymentMethod,

        @NotNull
        @JsonProperty("insurance")
        Set<String> insurance,

        @NotNull
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
