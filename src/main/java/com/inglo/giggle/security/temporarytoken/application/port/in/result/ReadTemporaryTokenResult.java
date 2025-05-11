package com.inglo.giggle.security.temporarytoken.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReadTemporaryTokenResult extends SelfValidating<ReadTemporaryTokenResult> {

    @JsonProperty("email")
    @NotBlank(message = "email 은 필수입니다.")
    private final String email;

    @JsonProperty("temporary_token")
    @NotBlank(message = "temporary_token 은 필수입니다.")
    private final String temporaryToken;

    public ReadTemporaryTokenResult(String email, String temporaryToken) {
        this.email = email;
        this.temporaryToken = temporaryToken;
        this.validateSelf();
    }
}
