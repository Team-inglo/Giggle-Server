package com.inglo.giggle.resume.application.port.in.usecase;

import com.inglo.giggle.resume.application.port.in.command.UpdateIntroductionCommand;

public interface UpdateUserIntroductionUseCase {

    /**
     * (유학생) 자기소개 수정하기 유스케이스
     */
    void execute(UpdateIntroductionCommand command);
}
