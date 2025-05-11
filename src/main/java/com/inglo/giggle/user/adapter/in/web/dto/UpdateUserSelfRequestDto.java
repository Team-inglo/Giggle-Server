package com.inglo.giggle.user.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.AddressRequestDto;

import java.time.LocalDate;

public record UpdateUserSelfRequestDto(

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

        @JsonProperty("visa")
        String visa,

        @JsonProperty("phone_number")
        String phoneNumber,

        @JsonProperty("is_profile_img_changed")
        Boolean isProfileImgChanged,

        @JsonProperty("address")
        AddressRequestDto address

) {
}
