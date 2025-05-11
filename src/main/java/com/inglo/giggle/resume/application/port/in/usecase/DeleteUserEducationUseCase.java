package com.inglo.giggle.resume.application.port.in.usecase;

import com.inglo.giggle.resume.application.port.in.command.DeleteEducationCommand;

public interface DeleteUserEducationUseCase {

    /**
     * (유학생) 학력 삭제하기 유스케이스
     */
    void execute(DeleteEducationCommand command);
}
