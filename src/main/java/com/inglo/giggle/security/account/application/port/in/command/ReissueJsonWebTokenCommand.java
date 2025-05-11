package com.inglo.giggle.security.account.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReissueJsonWebTokenCommand extends SelfValidating<ReissueJsonWebTokenCommand> {

    @NotBlank(message = "refresh_token 은 null일 수 없습니다.")
    private final String refreshToken;

    public ReissueJsonWebTokenCommand(
            String refreshToken
    ) {
        this.refreshToken = refreshToken;

        this.validateSelf();
    }
}
