package com.inglo.giggle.document.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserPartTimeEmploymentPermitRequestDto(

        @NotNull
        @Size(min = 1, max = 50)
        @JsonProperty("first_name")
        String firstName,

        @NotNull
        @Size(min = 1, max = 100)
        @JsonProperty("last_name")
        String lastName,

        @NotNull
        @Size(min = 1, max = 50)
        @JsonProperty("major")
        String major,

        @NotNull
        @JsonProperty("term_of_completion")
        Integer termOfCompletion,

        @NotNull
        @Size(min = 10, max = 20)
        @JsonProperty("phone_number")
        String phoneNumber,

        @NotNull
        @Email
        @Size(max = 320)
        @JsonProperty("email")
        String email
) {}
