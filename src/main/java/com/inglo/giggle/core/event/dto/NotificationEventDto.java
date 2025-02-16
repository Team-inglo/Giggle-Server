package com.inglo.giggle.core.event.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record NotificationEventDto(

        String title,

        String description,

        List<String> deviceToken
) {
    public static NotificationEventDto of(
            String title,
            String description,
            List<String> deviceToken
    ){
        return NotificationEventDto.builder()
                .title(title)
                .description(description)
                .deviceToken(deviceToken)
                .build();
    }
}
