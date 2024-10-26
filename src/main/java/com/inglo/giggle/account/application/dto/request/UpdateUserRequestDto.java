package com.inglo.giggle.account.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserRequestDto(

        @NotNull
        @Size(min = 1, max = 50)
        @JsonProperty("first_name")
        String firstName,

        @NotNull
        @Size(min = 1, max = 50)
        @JsonProperty("last_name")
        String lastName,

        @NotNull
        @JsonProperty("birth")
        String birth,

        @NotNull
        @JsonProperty("gender")
        String gender,

        @NotNull
        @Size(min = 1, max = 50)
        @JsonProperty("nationality")
        String nationality,

        @NotNull
        @JsonProperty("visa")
        String visa,

        @NotNull
        @Size(min = 10, max = 15)
        @JsonProperty("phone_number")
        String phoneNumber,

        @NotNull
        @JsonProperty("is_profile_img_changed")
        Boolean isProfileImgChanged
) {
}
