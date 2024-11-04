package com.inglo.giggle.address.domain.service;

import com.inglo.giggle.address.domain.Address;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    public Address createAddress(
            String addressName,
            String region1DepthName,
            String region2DepthName,
            String region3DepthName,
            String region4DepthName,
            String addressDetail,
            Double latitude,
            Double longitude
    ) {
        return Address.builder()
                .addressName(addressName)
                .region1DepthName(region1DepthName)
                .region2DepthName(region2DepthName)
                .region3DepthName(region3DepthName)
                .region4DepthName(region4DepthName)
                .addressDetail(addressDetail)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    public Address updateAddress(
            Address address,
            String addressName,
            String region1DepthName,
            String region2DepthName,
            String region3DepthName,
            String region4DepthName,
            String addressDetail,
            Double latitude,
            Double longitude
            ) {
        address.updateAddressName(addressName);
        address.updateRegion1DepthName(region1DepthName);
        address.updateRegion2DepthName(region2DepthName);
        address.updateRegion3DepthName(region3DepthName);
        address.updateRegion4DepthName(region4DepthName);
        address.updateAddressDetail(addressDetail);
        address.updateLongitude(longitude);
        address.updateLatitude(latitude);

        return address;
    }
}
