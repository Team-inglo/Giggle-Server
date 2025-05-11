package com.inglo.giggle.document.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateUserPartTimeEmploymentPermitRequestDto(

        @JsonProperty("first_name")
        String firstName,

        @JsonProperty("last_name")
        String lastName,

        @JsonProperty("major")
        String major,

        @JsonProperty("term_of_completion")
        Integer termOfCompletion,

        @JsonProperty("phone_number")
        String phoneNumber,

        @JsonProperty("email")
        String email
) {}
