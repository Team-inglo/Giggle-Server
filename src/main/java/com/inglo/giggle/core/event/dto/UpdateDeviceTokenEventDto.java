package com.inglo.giggle.core.event.dto;

import com.inglo.giggle.security.domain.mysql.AccountDevice;
import lombok.Builder;

@Builder
public record UpdateDeviceTokenEventDto(
        AccountDevice accountDevice
) {
    public static UpdateDeviceTokenEventDto of(
            AccountDevice accountDevice
    ) {
        return UpdateDeviceTokenEventDto.builder()
                .accountDevice(accountDevice)
                .build();
    }
}
