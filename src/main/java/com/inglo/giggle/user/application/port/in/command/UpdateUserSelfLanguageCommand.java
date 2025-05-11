package com.inglo.giggle.user.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateUserSelfLanguageCommand extends SelfValidating<UpdateUserSelfLanguageCommand> {

    @NotNull(message = "Account ID를 입력해주세요.")
    private final UUID accountId;

    @NotNull(message = "언어를 입력해주세요.")
    private final String language;

    public UpdateUserSelfLanguageCommand(UUID accountId, String language) {
        this.accountId = accountId;
        this.language = language;

        this.validateSelf();
    }
}
