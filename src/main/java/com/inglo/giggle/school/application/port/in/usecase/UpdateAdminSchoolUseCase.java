package com.inglo.giggle.school.application.port.in.usecase;

import com.inglo.giggle.school.application.port.in.command.UpdateAdminSchoolCommand;

public interface UpdateAdminSchoolUseCase {
    /**
     * 9.6 (어드민) 학교 수정하기 UseCase
     */
    void execute(UpdateAdminSchoolCommand command);
}
