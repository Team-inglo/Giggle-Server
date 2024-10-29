package com.inglo.giggle.address.domain;

import com.inglo.giggle.address.dto.request.AddressRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    //* -------------------------------------------- */
    //* Address Information Column ----------------- */
    //* -------------------------------------------- */
    @Column(name = "address_name", length = 50, nullable = false)
    private String addressName;

    @Column(name = "region_1depth_name", length = 10, nullable = false)
    private String region1DepthName;

    @Column(name = "region_2depth_name", length = 10, nullable = false)
    private String region2DepthName;

    @Column(name = "region_3depth_name", length = 10, nullable = false)
    private String region3DepthName;

    @Column(name = "region_4depth_name", length = 10)
    private String region4DepthName;

    @Column(name = "address_detail", length = 100, nullable = false)
    private String addressDetail;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Builder
    public Address(
            String addressName,
            String region1DepthName,
            String region2DepthName,
            String region3DepthName,
            String region4DepthName,
            String addressDetail,
            Double longitude,
            Double latitude
    ) {
        this.addressName = addressName;
        this.region1DepthName = region1DepthName;
        this.region2DepthName = region2DepthName;
        this.region3DepthName = region3DepthName;
        this.region4DepthName = region4DepthName;
        this.addressDetail = addressDetail;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getFullAddress() {
        return this.addressName + " " + this.addressDetail;
    }

    public void updateAddressName(String addressName) {
        this.addressName = addressName;
    }

    public void updateRegion1DepthName(String region1DepthName) {
        this.region1DepthName = region1DepthName;
    }

    public void updateRegion2DepthName(String region2DepthName) {
        this.region2DepthName = region2DepthName;
    }

    public void updateRegion3DepthName(String region3DepthName) {
        this.region3DepthName = region3DepthName;
    }

    public void updateRegion4DepthName(String region4DepthName) {
        this.region4DepthName = region4DepthName;
    }

    public void updateLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void updateLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void updateAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }
}
