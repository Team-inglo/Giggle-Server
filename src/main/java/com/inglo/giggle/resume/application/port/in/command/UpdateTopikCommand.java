package com.inglo.giggle.resume.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateTopikCommand extends SelfValidating<UpdateTopikCommand> {

    @NotNull(message = "accountId은 필수 입력 값입니다.")
    private final UUID accountId;

    @NotNull(message = "level은 필수 입력 값입니다.")
    @Max(value = 6, message = "level은 6 이하로 입력 가능합니다.")
    @Min(value = 0, message = "level은 0 이상으로 입력 가능합니다.")
    private final Integer level;

    public UpdateTopikCommand(
            UUID accountId,
            Integer level
    ) {
        this.accountId = accountId;
        this.level = level;
        this.validateSelf();
    }
}
