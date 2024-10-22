package com.inglo.giggle.core.domain.service;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.dto.AddressDto;
import org.springframework.stereotype.Service;

@Service
public class AddressDomainService {
    public Address createAddress(AddressDto addressDto) {
        return Address.builder()
                .addressName(addressDto.addressName())
                .region1DepthName(addressDto.region1DepthName())
                .region2DepthName(addressDto.region2DepthName())
                .region3DepthName(addressDto.region3DepthName())
                .region4DepthName(addressDto.region4DepthName())
                .addressDetail(addressDto.addressDetail())
                .longitude(addressDto.longitude())
                .latitude(addressDto.latitude())
                .build();
    }
}