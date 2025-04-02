package com.inglo.giggle.security.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record UpdateDeviceTokenRequestDto(

        @JsonProperty("device_token")
        @NotBlank(message = "deviceToken은 null일 수 없습니다.")
        String deviceToken,

        @JsonProperty("device_id")
        @NotBlank(message = "deviceId는 null일 수 없습니다.")
        String deviceId
) {
}
