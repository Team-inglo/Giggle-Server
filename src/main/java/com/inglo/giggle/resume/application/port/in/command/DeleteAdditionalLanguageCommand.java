package com.inglo.giggle.resume.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteAdditionalLanguageCommand extends SelfValidating<DeleteAdditionalLanguageCommand> {

    @NotNull(message = "accountId은 필수 입력 값입니다.")
    private final UUID accountId;

    @NotNull(message = "additional_language_skill_id는 필수 입력 값입니다.")
    @Positive(message = "additional_language_skill_id는 양수여야 합니다.")
    @Max(value = Long.MAX_VALUE, message = "additional_language_skill_id는 최대값을 초과할 수 없습니다.")
    private final Long additionalLanguageSkillId;

    public DeleteAdditionalLanguageCommand(
            UUID accountId,
            Long additionalLanguageSkillId
    ) {
        this.accountId = accountId;
        this.additionalLanguageSkillId = additionalLanguageSkillId;
        this.validateSelf();
    }
}
