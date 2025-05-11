package com.inglo.giggle.security.account.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateDeviceTokenRequestDto(

        @JsonProperty("device_token")
        String deviceToken,

        @JsonProperty("device_id")
        String deviceId
) {
}
