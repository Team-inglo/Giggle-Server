package com.inglo.giggle.notification.event;

import lombok.Builder;

import java.util.Map;

@Builder
public record NotificationEvent(

        String title,
        String description,
        String deviceToken
) {
    public Map<String, Object> toPayload() {
        if(deviceToken == null) {
            return Map.of(
                    "title", title,
                    "description", description
            );
        }
        return Map.of(
                "title", title,
                "description", description,
                "device_token", deviceToken
        );
    }
}
