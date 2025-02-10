package com.inglo.giggle.security.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record UpdateDeviceTokenRequestDto(
        @JsonProperty("device_token")
        @NotBlank(message = "deviceToken은 null일 수 없습니다.")
        String deviceToken,

        @JsonProperty("device_id")
        @NotBlank(message = "deviceId는 null일 수 없습니다.")
        String deviceId
) {
}
