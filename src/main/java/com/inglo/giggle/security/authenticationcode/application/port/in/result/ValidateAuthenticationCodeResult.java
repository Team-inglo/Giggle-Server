package com.inglo.giggle.security.authenticationcode.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ValidateAuthenticationCodeResult extends SelfValidating<ValidateAuthenticationCodeResult> {
    @JsonProperty("temporary_token")
    @NotBlank
    private final String temporaryToken;

    @Builder
    public ValidateAuthenticationCodeResult(String temporaryToken) {
        this.temporaryToken = temporaryToken;
        this.validateSelf();
    }
}
