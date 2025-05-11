package com.inglo.giggle.resume.application.port.in.usecase;

import com.inglo.giggle.resume.application.port.in.command.DeleteWorkExperienceCommand;

public interface DeleteUserWorkExperienceUseCase {

    /**
     * (유학생) 경력 삭제하기 유스케이스
     */
    void execute(DeleteWorkExperienceCommand command);
}
