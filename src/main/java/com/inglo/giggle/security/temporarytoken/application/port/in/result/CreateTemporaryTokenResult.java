package com.inglo.giggle.security.temporarytoken.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateTemporaryTokenResult extends SelfValidating<CreateTemporaryTokenResult> {
    @JsonProperty("temporary_token")
    @NotBlank(message = "temporary_token 은 필수입니다.")
    private final String temporaryToken;

    @Builder
    public CreateTemporaryTokenResult(String temporaryToken) {
        this.temporaryToken = temporaryToken;
        this.validateSelf();
    }
}
