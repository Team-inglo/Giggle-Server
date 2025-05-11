package com.inglo.giggle.resume.application.port.in.usecase;

import com.inglo.giggle.resume.application.port.in.command.UpdateSocialIntegrationProgramCommand;

public interface UpdateUserSocialIntegrationProgramUseCase {

    /**
     * (유학생) 언어 - SOCIAL INTEGRATION PROGRAM 레벨 수정하기 유스케이스
     */
    void execute(UpdateSocialIntegrationProgramCommand command);
}
