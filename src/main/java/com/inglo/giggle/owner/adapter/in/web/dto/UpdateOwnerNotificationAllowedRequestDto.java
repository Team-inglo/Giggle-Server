package com.inglo.giggle.owner.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateOwnerNotificationAllowedRequestDto(
        @JsonProperty("is_notification_allowed")
        Boolean isNotificationAllowed
) {
}
