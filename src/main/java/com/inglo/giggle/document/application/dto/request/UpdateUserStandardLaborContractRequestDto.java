package com.inglo.giggle.document.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.dto.request.AddressRequestDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserStandardLaborContractRequestDto(

        @NotNull
        @Size(min = 1, max = 50)
        @JsonProperty("first_name")
        String firstName,

        @NotNull
        @Size(min = 1, max = 100)
        @JsonProperty("last_name")
        String lastName,

        @NotNull
        @JsonProperty("address")
        AddressRequestDto address,

        @NotNull
        @Size(min = 10, max = 20)
        @JsonProperty("phone_number")
        String phoneNumber,

        @NotNull
        @JsonProperty("signature_base64")
        String signatureBase64
) {}
