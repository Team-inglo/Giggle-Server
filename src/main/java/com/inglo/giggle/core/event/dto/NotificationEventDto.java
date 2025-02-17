package com.inglo.giggle.core.event.dto;

import com.inglo.giggle.security.domain.mysql.AccountDevice;
import lombok.Builder;

import java.util.List;

@Builder
public record NotificationEventDto(

        String title,

        String description,

        List<AccountDevice> accountDevices
) {
    public static NotificationEventDto of(
            String title,
            String description,
            List<AccountDevice> accountDevices
    ){
        return NotificationEventDto.builder()
                .title(title)
                .description(description)
                .accountDevices(accountDevices)
                .build();
    }
}
