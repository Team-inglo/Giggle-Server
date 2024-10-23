package com.inglo.giggle.address.domain.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.dto.request.AddressRequestDto;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    public Address createAddress(AddressRequestDto addressRequestDto) {
        return Address.builder()
                .addressName(addressRequestDto.addressName())
                .region1DepthName(addressRequestDto.region1DepthName())
                .region2DepthName(addressRequestDto.region2DepthName())
                .region3DepthName(addressRequestDto.region3DepthName())
                .region4DepthName(addressRequestDto.region4DepthName())
                .addressDetail(addressRequestDto.addressDetail())
                .longitude(addressRequestDto.longitude())
                .latitude(addressRequestDto.latitude())
                .build();
    }
}
