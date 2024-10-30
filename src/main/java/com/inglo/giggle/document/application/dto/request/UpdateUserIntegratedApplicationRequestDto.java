package com.inglo.giggle.document.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.dto.request.AddressRequestDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateUserIntegratedApplicationRequestDto(
        @NotNull
        @Size(min = 1, max = 50)
        @JsonProperty("first_name")
        String firstName,

        @NotNull
        @Size(min = 1, max = 100)
        @JsonProperty("last_name")
        String lastName,

        @NotNull
        @JsonProperty("birth")
        LocalDate birth,

        @NotNull
        @JsonProperty("gender")
        String gender,

        @NotNull
        @Size(min = 1, max = 56)
        @JsonProperty("nationality")
        String nationality,

        @NotNull
        @Size(min = 10, max = 20)
        @JsonProperty("tele_phone_number")
        String telePhoneNumber,

        @NotNull
        @Size(min = 10, max = 20)
        @JsonProperty("cell_phone_number")
        String cellPhoneNumber,

        @NotNull
        @JsonProperty("is_accredited")
        Boolean isAccredited,

        @NotNull
        @JsonProperty("school_name")
        String schoolName,

        @NotNull
        @JsonProperty("school_phone_number")
        String schoolPhoneNumber,

        @NotNull
        @Size(min = 1, max = 50)
        @JsonProperty("new_work_place_name")
        String newWorkPlaceName,

        @NotNull
        @Size(min = 1, max = 12)
        @JsonProperty("new_work_place_registration_number")
        String newWorkPlaceRegistrationNumber,

        @NotNull
        @Size(min = 10, max = 20)
        @JsonProperty("new_work_place_phone_number")
        String newWorkPlacePhoneNumber,

        @NotNull
        @JsonProperty("annual_income_amount")
        Integer annualIncomeAmount,

        @NotNull
        @Size(min = 1, max = 30)
        @JsonProperty("occupation")
        String occupation,

        @NotNull
        @Email
        @Size(max = 320)
        @JsonProperty("email")
        String email,

        @NotNull
        @JsonProperty("signature_base64")
        String signatureBase64,

        @NotNull
        @JsonProperty("address")
        AddressRequestDto address
) {
}
