package com.inglo.giggle.core.event.dto;

import com.inglo.giggle.security.domain.mysql.AccountDevice;
import lombok.Builder;

@Builder
public record DeregisterDeviceTokenEventDto(
        AccountDevice accountDevice
) {
    public static DeregisterDeviceTokenEventDto of(
            AccountDevice accountDevice
    ) {
        return DeregisterDeviceTokenEventDto.builder()
                .accountDevice(accountDevice)
                .build();
    }
}
