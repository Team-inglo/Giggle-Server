package com.inglo.giggle.security.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
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
