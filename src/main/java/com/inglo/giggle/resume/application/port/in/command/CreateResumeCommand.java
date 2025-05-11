package com.inglo.giggle.resume.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateResumeCommand extends SelfValidating<CreateResumeCommand> {

    @NotNull(message = "사용자 id는 필수입니다.")
    private final UUID accountId;

    public CreateResumeCommand(UUID accountId) {
        this.accountId = accountId;
        this.validateSelf();
    }
}
