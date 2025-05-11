package com.inglo.giggle.school.application.port.in.usecase;

import com.inglo.giggle.school.application.port.in.command.DeleteAdminSchoolCommand;

public interface DeleteAdminSchoolUseCase {
    /**
     * 9.7 (어드민) 학교 삭제하기 UseCase
     */
    void execute(DeleteAdminSchoolCommand command);
}
