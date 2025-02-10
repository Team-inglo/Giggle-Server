package com.inglo.giggle.account.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.domain.Address;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateUserRequestDto(

        @NotNull
        @Size(min = 1, max = 50)
        @JsonProperty("first_name")
        String firstName,

        @NotNull
        @Size(min = 1, max = 100)
        @JsonProperty("last_name")
        String lastName,

        @JsonProperty("birth")
        LocalDate birth,

        @NotNull
        @JsonProperty("gender")
        String gender,

        @Size(min = 1, max = 56)
        @JsonProperty("nationality")
        String nationality,

        @NotNull
        @JsonProperty("visa")
        String visa,

        @NotNull
        @Size(min = 10, max = 20)
        @JsonProperty("phone_number")
        String phoneNumber,

        @NotNull
        @JsonProperty("is_profile_img_changed")
        Boolean isProfileImgChanged,

        @JsonProperty("address")
        AddressDto address

) {
        public record AddressDto(
                @NotNull
                @JsonProperty("address_name")
                String addressName,

                @NotNull
                @JsonProperty("region_1depth_name")
                String region1DepthName,

                @NotNull
                @JsonProperty("region_2depth_name")
                String region2DepthName,

                @NotNull
                @JsonProperty("region_3depth_name")
                String region3DepthName,

                @JsonProperty("region_4depth_name")
                String region4DepthName,

                @NotNull(message = "상세주소를 입력해주세요.")
                @Size(max = 100, message = "상세주소는 100자 이하로 입력해주세요.")
                @JsonProperty("address_detail")
                String addressDetail,

                @NotNull
                @JsonProperty("longitude")
                Double longitude,

                @NotNull
                @JsonProperty("latitude")
                Double latitude
        ) {
                public Address toEntity() {
                        return Address.builder()
                                .addressName(addressName)
                                .region1DepthName(region1DepthName)
                                .region2DepthName(region2DepthName)
                                .region3DepthName(region3DepthName)
                                .region4DepthName(region4DepthName)
                                .addressDetail(addressDetail)
                                .longitude(longitude)
                                .latitude(latitude)
                                .build();
                }
        }
}
