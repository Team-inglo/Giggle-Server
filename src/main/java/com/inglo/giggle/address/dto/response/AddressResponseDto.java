package com.inglo.giggle.address.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public class AddressResponseDto extends SelfValidating<AddressResponseDto> {
    @JsonProperty("address_name")
    @NotBlank
    String addressName;

    @JsonProperty("region_1depth_name")
    @NotBlank
    String region1DepthName;

    @JsonProperty("region_2depth_name")
    @NotBlank
    String region2DepthName;

    @JsonProperty("region_3depth_name")
    @NotBlank
    String region3DepthName;

    @JsonProperty("region_4depth_name")
    @NotBlank
    String region4DepthName;

    @JsonProperty("address_detail")
    @NotBlank
    String addressDetail;

    @JsonProperty("latitude")
    @NotNull
    Double latitude;

    @JsonProperty("longitude")
    @NotNull
    Double longitude;
    @Builder
    public AddressResponseDto(
            String addressName,
            String region1DepthName,
            String region2DepthName,
            String region3DepthName,
            String region4DepthName,
            String addressDetail,
            Double latitude,
            Double longitude
    ) {
        this.addressName = addressName;
        this.region1DepthName = region1DepthName;
        this.region2DepthName = region2DepthName;
        this.region3DepthName = region3DepthName;
        this.region4DepthName = region4DepthName;
        this.addressDetail = addressDetail;
        this.latitude = latitude;
        this.longitude = longitude;
        this.validateSelf();
    }
}
