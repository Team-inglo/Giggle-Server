package com.inglo.giggle.document.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.AddressRequestDto;

public record UpdateUserStandardLaborContractRequestDto(

        @JsonProperty("first_name")
        String firstName,

        @JsonProperty("last_name")
        String lastName,

        @JsonProperty("address")
        AddressRequestDto address,

        @JsonProperty("phone_number")
        String phoneNumber,

        @JsonProperty("signature_base64")
        String signatureBase64
) {}
