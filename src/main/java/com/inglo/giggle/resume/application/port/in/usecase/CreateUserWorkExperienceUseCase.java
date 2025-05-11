package com.inglo.giggle.resume.application.port.in.usecase;

import com.inglo.giggle.resume.application.port.in.command.CreateWorkExperienceCommand;

public interface CreateUserWorkExperienceUseCase {

    /**
     * (유학생) 경력 추가하기 유스케이스
     */
    void execute(CreateWorkExperienceCommand command);
}
