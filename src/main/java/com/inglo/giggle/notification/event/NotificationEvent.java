package com.inglo.giggle.notification.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Map;

@Builder
public record NotificationEvent(

        String title,
        String description,

        @JsonProperty("device_token")
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
