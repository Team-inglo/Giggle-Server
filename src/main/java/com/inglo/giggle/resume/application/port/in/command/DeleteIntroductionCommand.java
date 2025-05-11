package com.inglo.giggle.resume.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteIntroductionCommand extends SelfValidating<DeleteIntroductionCommand> {
    @NotNull(message = "accountId은 필수 입력 값입니다.")
    private final UUID accountId;

    public DeleteIntroductionCommand(
            UUID accountId
    ) {
        this.accountId = accountId;
        this.validateSelf();
    }
}
