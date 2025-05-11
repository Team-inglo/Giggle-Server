package com.inglo.giggle.security.account.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReissuePasswordCommand extends SelfValidating<ReissuePasswordCommand> {

    @NotNull(message = "temporary_token 은 null일 수 없습니다.")
    private final String temporaryToken;

    public ReissuePasswordCommand(String temporaryToken) {
        this.temporaryToken = temporaryToken;

        this.validateSelf();
    }
}
