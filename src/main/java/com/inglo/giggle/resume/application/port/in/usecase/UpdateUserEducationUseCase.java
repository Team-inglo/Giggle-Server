package com.inglo.giggle.resume.application.port.in.usecase;

import com.inglo.giggle.resume.application.port.in.command.UpdateEducationCommand;

public interface UpdateUserEducationUseCase {

    /**
     * (유학생) 학력 수정하기 유스케이스
     */
    void execute(UpdateEducationCommand command);
}
