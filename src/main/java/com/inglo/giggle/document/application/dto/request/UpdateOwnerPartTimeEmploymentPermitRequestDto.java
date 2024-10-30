package com.inglo.giggle.document.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.dto.request.AddressRequestDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateOwnerPartTimeEmploymentPermitRequestDto(

        @NotNull
        @Size(min = 1, max = 150)
        @JsonProperty("company_name")
        String companyName,

        @NotNull
        @Size(min = 1, max = 12)
        @JsonProperty("company_registration_number")
        String companyRegistrationNumber,

        @NotNull
        @Size(min = 1, max = 20)
        @JsonProperty("job_type")
        String jobType,

        @NotNull
        @Size(min = 1, max = 100)
        @JsonProperty("name")
        String name,

        @NotNull
        @Size(min = 10, max = 20)
        @JsonProperty("phone_number")
        String phoneNumber,

        @NotNull
        @JsonProperty("signature_base64")
        String signatureBase64,

        @NotNull
        @JsonProperty("work_period")
        String workPeriod,

        @NotNull
        @JsonProperty("hourly_rate")
        Integer hourlyRate,

        @NotNull
        @Size(min = 1, max = 150)
        @JsonProperty("work_days_weekdays")
        String workDaysWeekdays,

        @NotNull
        @Size(min = 1, max = 150)
        @JsonProperty("work_days_weekends")
        String workDaysWeekends,

        @NotNull
        @JsonProperty("address")
        AddressRequestDto address

) {
}
