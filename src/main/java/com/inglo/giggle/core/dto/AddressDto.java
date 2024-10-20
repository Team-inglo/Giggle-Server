package com.inglo.giggle.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressDto(
        @JsonProperty("address_name")
        @NotBlank(message = "주소를 입력해주세요.")
        String addressName,

        @JsonProperty("region1_depth_name")
        @NotBlank(message = "시/도를 입력해주세요.")
        String region1DepthName,

        @JsonProperty("region2_depth_name")
        @NotBlank(message = "군/구를 입력해주세요.")
        String region2DepthName,

        @JsonProperty("region3_depth_name")
        @NotBlank(message = "읍/면/동을 입력해주세요.")
        String region3DepthName,

        @JsonProperty("region4_depth_name")
        @NotBlank(message = "리/가/동을 입력해주세요.")
        String region4DepthName,

        @JsonProperty("address_detail")
        @NotBlank(message = "상세 주소를 입력해주세요.")
        String addressDetail,

        @JsonProperty("latitude")
        @NotNull(message = "위도를 입력해주세요.")
        Double latitude,

        @JsonProperty("longitude")
        @NotNull(message = "경도를 입력해주세요.")
        Double longitude
) {
}
