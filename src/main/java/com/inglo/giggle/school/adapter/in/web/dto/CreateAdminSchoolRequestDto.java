package com.inglo.giggle.school.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.AddressRequestDto;

public record CreateAdminSchoolRequestDto(
        @JsonProperty("school_name")
        String schoolName,

        @JsonProperty("school_phone_number")
        String schoolPhoneNumber,

        @JsonProperty("is_metropolitan")
        Boolean isMetropolitan,

        @JsonProperty("institute_name")
        String instituteName,

        @JsonProperty("coordinator_name")
        String coordinatorName,

        @JsonProperty("coordinator_phone_number")
        String coordinatorPhoneNumber,

        @JsonProperty("address")
        AddressRequestDto address
) {
}
