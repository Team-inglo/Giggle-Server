package com.inglo.giggle.core.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Address {
    private String addressName;
    private String region1DepthName;
    private String region2DepthName;
    private String region3DepthName;
    private String region4DepthName;
    private String addressDetail;
    private Double longitude;
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
        String safeName = this.addressName == null ? "" : this.addressName;
        String safeDetail = this.addressDetail == null ? "" : this.addressDetail;
        return safeName + " " + safeDetail;
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

    public void updateAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }
}

