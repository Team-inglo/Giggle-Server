package com.inglo.giggle.resume.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class CreateWorkExperienceCommand extends SelfValidating<CreateWorkExperienceCommand> {

    @NotNull(message = "accountId은 필수 입력 값입니다.")
    private final UUID accountId;

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

    public CreateWorkExperienceCommand(
            UUID accountId,
            String title,
            String workplace,
            LocalDate startDate,
            LocalDate endDate,
            String description
    ) {
        this.accountId = accountId;
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
