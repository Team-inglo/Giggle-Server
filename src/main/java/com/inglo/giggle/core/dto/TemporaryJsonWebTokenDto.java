package com.inglo.giggle.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TemporaryJsonWebTokenDto extends SelfValidating<TemporaryJsonWebTokenDto> {
    @JsonProperty("temporary_token")
    @NotBlank
    private final String temporaryToken;

    @Builder
    public TemporaryJsonWebTokenDto(String temporaryToken) {
        this.temporaryToken = temporaryToken;
        this.validateSelf();
    }
}
