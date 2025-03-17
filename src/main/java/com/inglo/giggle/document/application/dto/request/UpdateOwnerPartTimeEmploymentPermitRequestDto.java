package com.inglo.giggle.document.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.dto.request.AddressRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateOwnerPartTimeEmploymentPermitRequestDto(

        @NotNull(message = "회사/점포명을 입력해주세요.")
        @Size(min = 1, max = 150, message = "회사/점포명은 150자 이하로 입력해주세요.")
        @JsonProperty("company_name")
        String companyName,

        @NotNull(message = "사업자 등록번호를 입력해주세요.")
        @Size(min = 1, max = 12, message = "사업자 등록번호는 12자 이하로 입력해주세요.")
        @JsonProperty("company_registration_number")
        String companyRegistrationNumber,

        @NotNull(message = "직종을 입력해주세요.")
        @Size(min = 1, max = 20, message = "직종은 20자 이하로 입력해주세요.")
        @JsonProperty("job_type")
        String jobType,

        @NotNull(message = "이름을 입력해주세요.")
        @Size(min = 1, max = 100, message = "이름은 100자 이하로 입력해주세요.")
        @JsonProperty("name")
        String name,

        @NotNull(message = "전화번호를 입력해주세요.")
        @Size(min = 10, max = 20, message = "전화번호는 10자 이상 20자 이하로 입력해주세요.")
        @JsonProperty("phone_number")
        String phoneNumber,

        @NotNull(message = "서명을 입력해주세요.")
        @JsonProperty("signature_base64")
        String signatureBase64,

        @NotNull(message = "근무기간을 입력해주세요.")
        @JsonProperty("work_period")
        String workPeriod,

        @NotNull(message = "시급을 입력해주세요.")
        @Max(value = 2000000000, message = "시급은 2,000,000,000원 이하로 입력해주세요.")
        @JsonProperty("hourly_rate")
        Integer hourlyRate,

        @NotNull(message = "평일 근무시간을 입력해주세요.")
        @Size(min = 1, max = 150, message = "평일 근무시간은 150자 이하로 입력해주세요.")
        @JsonProperty("work_days_weekdays")
        String workDaysWeekdays,

        @NotNull(message = "주말 근무시간을 입력해주세요.")
        @Size(min = 1, max = 150, message = "주말 근무시간은 150자 이하로 입력해주세요.")
        @JsonProperty("work_days_weekends")
        String workDaysWeekends,

        @NotNull(message = "주소를 입력해주세요.")
        @JsonProperty("address")
        @Valid
        AddressRequestDto address

) {
}
