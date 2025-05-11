package com.inglo.giggle.resume.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateIntroductionCommand extends SelfValidating<UpdateIntroductionCommand> {
    @NotNull(message = "accountId은 필수 입력 값입니다.")
    private final UUID accountId;

    @Size(min = 1, max = 200, message = "자기소개는 1자 이상 200자 이하로 입력 가능합니다.")
    private final String introduction;

    public UpdateIntroductionCommand(
            UUID accountId,
            String introduction
    ) {
        this.accountId = accountId;
        this.introduction = introduction;
        this.validateSelf();
    }
}
