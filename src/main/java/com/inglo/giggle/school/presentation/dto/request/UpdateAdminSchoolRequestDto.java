package com.inglo.giggle.school.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.dto.request.AddressRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateAdminSchoolRequestDto(
        @NotBlank(message = "학교 이름은 필수입니다.")
        @JsonProperty("school_name")
        String schoolName,

        @NotBlank(message = "학교 전화번호는 필수입니다.")
        @JsonProperty("school_phone_number")
        String schoolPhoneNumber,

        @NotNull(message = "수도권 여부는 필수입니다.")
        @JsonProperty("is_metropolitan")
        Boolean isMetropolitan,

        @JsonProperty("institute_name")
        String instituteName,

        @JsonProperty("coordinator_name")
        String coordinatorName,

        @JsonProperty("coordinator_phone_number")
        String coordinatorPhoneNumber,

        @JsonProperty("address")
        @Valid
        AddressRequestDto address
) {
}
