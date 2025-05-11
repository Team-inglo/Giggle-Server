package com.inglo.giggle.security.account.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReissueJsonWebTokenResult extends SelfValidating<ReissueJsonWebTokenResult> {
    @JsonProperty("access_token")
    @NotBlank(message = "access_token 은 필수입니다.")
    private final String accessToken;

    @JsonProperty("refresh_token")
    @NotBlank(message = "refresh_token 은 필수입니다.")
    private final String refreshToken;

    @Builder
    public ReissueJsonWebTokenResult(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.validateSelf();
    }

    public static ReissueJsonWebTokenResult of(
            String accessToken,
            String refreshToken
    ) {
        return new ReissueJsonWebTokenResult(accessToken, refreshToken);
    }
}
