package com.inglo.giggle.account.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record UpdateNotificationAllowedRequestDto(
        @JsonProperty("is_notification_allowed")
        @NotNull
        Boolean isNotificationAllowed
) {
}
