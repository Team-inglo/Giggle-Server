package com.inglo.giggle.school.application.port.in.usecase;

import com.inglo.giggle.school.application.port.in.command.CreateAdminSchoolCommand;

public interface CreateAdminSchoolUseCase {

    /**
     * 9.5 (어드민) 학교 생성하기 UseCase
     */
    void execute(CreateAdminSchoolCommand command);
}
