package com.inglo.giggle.document.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.AddressRequestDto;

public record UpdateOwnerPartTimeEmploymentPermitRequestDto(

        @JsonProperty("company_name")
        String companyName,

        @JsonProperty("company_registration_number")
        String companyRegistrationNumber,

        @JsonProperty("job_type")
        String jobType,

        @JsonProperty("name")
        String name,

        @JsonProperty("phone_number")
        String phoneNumber,

        @JsonProperty("signature_base64")
        String signatureBase64,

        @JsonProperty("work_period")
        String workPeriod,

        @JsonProperty("hourly_rate")
        Integer hourlyRate,

        @JsonProperty("work_days_weekdays")
        String workDaysWeekdays,

        @JsonProperty("work_days_weekends")
        String workDaysWeekends,

        @JsonProperty("address")
        AddressRequestDto address

) {
}
