package com.inglo.giggle.resume.application.port.in.usecase;

import com.inglo.giggle.resume.application.port.in.command.CreateEducationCommand;

public interface CreateUserEducationUseCase {

    /**
     * (유학생) 학력 생성하기 유스케이스
     */
    void execute(CreateEducationCommand command);
}
