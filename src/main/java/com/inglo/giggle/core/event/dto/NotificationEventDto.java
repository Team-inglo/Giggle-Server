package com.inglo.giggle.core.event.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record NotificationEventDto(

        String title,

        String description,

        List<String> deviceTokens
) {
    public static NotificationEventDto of(
            String title,
            String description,
            List<String> deviceTokens
    ){
        return NotificationEventDto.builder()
                .title(title)
                .description(description)
                .deviceTokens(deviceTokens)
                .build();
    }
}
