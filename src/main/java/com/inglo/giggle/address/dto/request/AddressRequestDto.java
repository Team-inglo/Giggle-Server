package com.inglo.giggle.address.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.domain.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record AddressRequestDto(
        @JsonProperty("address_name")
        @NotBlank(message = "주소를 입력해주세요.")
        String addressName,

        @JsonProperty("region_1depth_name")
        @NotBlank(message = "시/도를 입력해주세요.")
        String region1DepthName,

        @JsonProperty("region_2depth_name")
        @NotBlank(message = "군/구를 입력해주세요.")
        String region2DepthName,

        @JsonProperty("region_3depth_name")
        @NotBlank(message = "읍/면/동을 입력해주세요.")
        String region3DepthName,

        @JsonProperty("region_4depth_name")
        String region4DepthName,

        @JsonProperty("address_detail")
        @Size(max = 50, message = "상세 주소는 50자 이내로 입력해주세요.")
        String addressDetail,

        @JsonProperty("latitude")
        @NotNull(message = "위도를 입력해주세요.")
        Double latitude,

        @JsonProperty("longitude")
        @NotNull(message = "경도를 입력해주세요.")
        Double longitude
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
