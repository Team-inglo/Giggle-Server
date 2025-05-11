package com.inglo.giggle.resume.application.port.in.usecase;

import com.inglo.giggle.resume.application.port.in.command.DeleteIntroductionCommand;

public interface DeleteUserIntroductionUseCase {

    /**
     * (유학생) 자기소개 삭제하기 유스케이스
     */
    void execute(DeleteIntroductionCommand command);
}
