package com.inglo.giggle.resume.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteWorkExperienceCommand extends SelfValidating<DeleteWorkExperienceCommand> {

    @NotNull(message = "accountId은 필수 입력 값입니다.")
    private final UUID accountId;

    @NotNull(message = "work_experience_id은 필수 입력 값입니다.")
    @Positive(message = "work_experience_id은 양수여야 합니다.")
    @Max(value = Long.MAX_VALUE, message = "work_experience_id은 최대값을 초과할 수 없습니다.")
    private final Long workExperienceId;

    public DeleteWorkExperienceCommand(
            UUID accountId,
            Long workExperienceId
    ) {
        this.accountId = accountId;
        this.workExperienceId = workExperienceId;
        this.validateSelf();
    }
}
