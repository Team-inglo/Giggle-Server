package com.inglo.giggle.resume.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class UpdateWorkExperienceCommand extends SelfValidating<UpdateWorkExperienceCommand> {
    @NotNull(message = "accountId은 필수 입력 값입니다.")
    private final UUID accountId;

    @NotNull(message = "work_experience_id은 필수 입력 값입니다.")
    @Positive(message = "work_experience_id은 양수여야 합니다.")
    @Max(value = Long.MAX_VALUE, message = "work_experience_id는 최대값을 초과할 수 없습니다.")
    private final Long workExperienceId;

    @NotNull(message = "title은 필수 입력 값입니다.")
    @Size(min = 1, max = 20, message = "title은 1자 이상 20자 이하로 입력 가능합니다.")
    private final String title;

    @NotNull(message = "workplace은 필수 입력 값입니다.")
    @Size(min = 1, max = 20, message = "workplace은 1자 이상 20자 이하로 입력 가능합니다.")
    private final String workplace;

    @NotNull(message = "start_date은 필수 입력 값입니다.")
    private final LocalDate startDate;

    private final LocalDate endDate;

    @Size(min = 1, max = 200, message = "description은 1자 이상 200자 이하로 입력 가능합니다.")
    private final String description;

    public UpdateWorkExperienceCommand(
            UUID accountId,
            Long workExperienceId,
            String title,
            String workplace,
            LocalDate startDate,
            LocalDate endDate,
            String description
    ) {
        this.accountId = accountId;
        this.workExperienceId = workExperienceId;
        this.title = title;
        this.workplace = workplace;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new CommonException(ErrorCode.INVALID_DATE_RANGE);
        }

        this.validateSelf();
    }
}
