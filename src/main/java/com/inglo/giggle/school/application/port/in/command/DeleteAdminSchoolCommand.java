package com.inglo.giggle.school.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeleteAdminSchoolCommand extends SelfValidating<DeleteAdminSchoolCommand> {

    @NotNull(message = "학교 id는 필수입니다.")
    private final Long schoolId;

    public DeleteAdminSchoolCommand(
            Long schoolId
    ) {
        this.schoolId = schoolId;

        this.validateSelf();
    }
}
