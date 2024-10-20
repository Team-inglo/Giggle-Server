package com.inglo.giggle.core.domain;

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
    private String region1depthName;

    @Column(name = "region_2depth_name", length = 10, nullable = false)
    private String region2depthName;

    @Column(name = "region_3depth_name", length = 10, nullable = false)
    private String region3depthName;

    @Column(name = "region_4depth_name", length = 10)
    private String region4depthName;

    @Column(name = "address_detail", length = 100, nullable = false)
    private String addressDetail;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Builder
    public Address(
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
}
