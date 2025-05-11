package com.inglo.giggle.security.account.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignUpDefaultOwnerResult extends SelfValidating<SignUpDefaultOwnerResult> {

    @JsonProperty("access_token")
    @NotBlank(message = "access_token 은 필수입니다.")
    private final String accessToken;

    @JsonProperty("refresh_token")
    @NotBlank(message = "refresh_token 은 필수입니다.")
    private final String refreshToken;

    public SignUpDefaultOwnerResult(
            String accessToken,
            String refreshToken
    ) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.validateSelf();
    }
}
