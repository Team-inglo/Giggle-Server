package com.inglo.giggle.user.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateUserSelfNotificationAllowedRequestDto(
        @JsonProperty("is_notification_allowed")
        Boolean isNotificationAllowed
) {
}
