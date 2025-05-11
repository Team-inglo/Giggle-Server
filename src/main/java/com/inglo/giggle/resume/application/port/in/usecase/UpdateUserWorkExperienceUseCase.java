package com.inglo.giggle.resume.application.port.in.usecase;

import com.inglo.giggle.resume.application.port.in.command.UpdateWorkExperienceCommand;

public interface UpdateUserWorkExperienceUseCase {

    /**
     * (유학생) 경력 수정하기 유스케이스
     */
    void execute(UpdateWorkExperienceCommand command);
}
