package com.inglo.giggle.address.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.persistence.entity.AddressEntity;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddressResponseDto extends SelfValidating<AddressResponseDto> {

    @NotBlank(message = "address_name은 null일 수 없습니다.")
    @JsonProperty("address_name")
    private final String addressName;

    @NotBlank(message = "region_1depth_name은 null일 수 없습니다.")
    @JsonProperty("region_1depth_name")
    private final String region1depthName;

    @NotBlank(message = "region_2depth_name은 null일 수 없습니다.")
    @JsonProperty("region_2depth_name")
    private final String region2depthName;

    @NotBlank(message = "region_3depth_name은 null일 수 없습니다.")
    @JsonProperty("region_3depth_name")
    private final String region3depthName;

    @JsonProperty("region_4depth_name")
    private final String region4depthName;

    @JsonProperty("address_detail")
    private final String addressDetail;

    @NotNull(message = "longitude는 null일 수 없습니다.")
    @JsonProperty("longitude")
    private final Double longitude;

    @NotNull(message = "latitude는 null일 수 없습니다.")
    @JsonProperty("latitude")
    private final Double latitude;

    @Builder
    public AddressResponseDto(
            String addressName,
            String region1depthName,
            String region2depthName,
            String region3depthName,
            String region4depthName,
            String addressDetail,
            Double longitude,
            Double latitude
    ) {
        this.addressName = addressName;
        this.region1depthName = region1depthName;
        this.region2depthName = region2depthName;
        this.region3depthName = region3depthName;
        this.region4depthName = region4depthName;
        this.addressDetail = addressDetail;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static AddressResponseDto from(Address address) {
        return AddressResponseDto.builder()
                .addressName(address.getAddressName())
                .region1depthName(address.getRegion1DepthName())
                .region2depthName(address.getRegion2DepthName())
                .region3depthName(address.getRegion3DepthName())
                .region4depthName(address.getRegion4DepthName())
                .addressDetail(address.getAddressDetail())
                .longitude(address.getLongitude())
                .latitude(address.getLatitude())
                .build();
    }
}
