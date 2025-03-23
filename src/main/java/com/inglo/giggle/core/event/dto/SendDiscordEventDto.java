package com.inglo.giggle.core.event.dto;

import lombok.Builder;

@Builder
public record SendDiscordEventDto(
        Throwable e
) {
    public static SendDiscordEventDto of(
            Throwable e
    ) {
        return SendDiscordEventDto.builder()
                .e(e)
                .build();
    }
}
