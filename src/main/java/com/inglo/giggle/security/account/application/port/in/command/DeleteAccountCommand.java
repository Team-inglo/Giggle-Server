package com.inglo.giggle.security.account.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteAccountCommand extends SelfValidating<DeleteAccountCommand> {

    @NotNull(message = "accountId 은 null일 수 없습니다.")
    private UUID accountId;

    public DeleteAccountCommand(
            UUID accountId
    ) {
        this.accountId = accountId;

        this.validateSelf();
    }
}
