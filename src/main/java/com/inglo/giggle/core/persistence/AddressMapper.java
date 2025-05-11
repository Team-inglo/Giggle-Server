package com.inglo.giggle.core.persistence;

import com.inglo.giggle.core.domain.Address;

public class AddressMapper {
    public static AddressEntity toEntity(Address domain) {
        return AddressEntity.builder()
                .addressName(domain.getAddressName())
                .region1DepthName(domain.getRegion1DepthName())
                .region2DepthName(domain.getRegion2DepthName())
                .region3DepthName(domain.getRegion3DepthName())
                .region4DepthName(domain.getRegion4DepthName())
                .addressDetail(domain.getAddressDetail())
                .longitude(domain.getLongitude())
                .latitude(domain.getLatitude())
                .build();
    }

    public static Address toDomain(AddressEntity entity) {
        return Address.builder()
                .addressName(entity.getAddressName())
                .region1DepthName(entity.getRegion1DepthName())
                .region2DepthName(entity.getRegion2DepthName())
                .region3DepthName(entity.getRegion3DepthName())
                .region4DepthName(entity.getRegion4DepthName())
                .addressDetail(entity.getAddressDetail())
                .longitude(entity.getLongitude())
                .latitude(entity.getLatitude())
                .build();
    }
}
