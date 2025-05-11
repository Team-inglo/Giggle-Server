package com.inglo.giggle.resume.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteEducationCommand extends SelfValidating<DeleteEducationCommand> {

    @NotNull(message = "accountId은 필수 입력 값입니다.")
    private final UUID accountId;

    @NotNull(message = "education_id는 필수 입력 값입니다.")
    @Positive(message = "education_id는 양수여야 합니다.")
    @Max(value = Long.MAX_VALUE, message = "education_id는 최대값을 초과할 수 없습니다.")
    private final Long educationId;

    public DeleteEducationCommand(
            UUID accountId,
            Long educationId
    ) {
        this.accountId = accountId;
        this.educationId = educationId;
        this.validateSelf();
    }
}
