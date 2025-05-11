package com.inglo.giggle.document.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.AddressRequestDto;

import java.time.LocalDate;

public record CreateUserIntegratedApplicationRequestDto(
        @JsonProperty("first_name")
        String firstName,

        @JsonProperty("last_name")
        String lastName,

        @JsonProperty("birth")
        LocalDate birth,

        @JsonProperty("gender")
        String gender,

        @JsonProperty("nationality")
        String nationality,

        @JsonProperty("tele_phone_number")
        String telePhoneNumber,

        @JsonProperty("cell_phone_number")
        String cellPhoneNumber,

        @JsonProperty("is_accredited")
        Boolean isAccredited,

        @JsonProperty("school_name")
        String schoolName,

        @JsonProperty("school_phone_number")
        String schoolPhoneNumber,

        @JsonProperty("new_work_place_name")
        String newWorkPlaceName,

        @JsonProperty("new_work_place_registration_number")
        String newWorkPlaceRegistrationNumber,

        @JsonProperty("new_work_place_phone_number")
        String newWorkPlacePhoneNumber,

        @JsonProperty("annual_income_amount")
        Integer annualIncomeAmount,

        @JsonProperty("occupation")
        String occupation,

        @JsonProperty("email")
        String email,

        @JsonProperty("signature_base64")
        String signatureBase64,

        @JsonProperty("address")
        AddressRequestDto address

) {
}
