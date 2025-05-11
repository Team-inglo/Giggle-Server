package com.inglo.giggle.termaccount.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class CreateTermAccountCommand extends SelfValidating<CreateTermAccountCommand> {

    @NotNull(message = "account_id는 null일 수 없습니다.")
    private final UUID accountId;

    @NotBlank(message = "term_types는 null일 수 없습니다.")
    private final List<String> termTypes;

    public CreateTermAccountCommand(UUID accountId, List<String> termTypes) {
        this.accountId = accountId;
        this.termTypes = termTypes;

        this.validateSelf();
    }
}
