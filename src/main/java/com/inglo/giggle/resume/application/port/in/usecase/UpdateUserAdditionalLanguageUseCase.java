package com.inglo.giggle.resume.application.port.in.usecase;

import com.inglo.giggle.resume.application.port.in.command.UpdateAdditionalLanguageCommand;

public interface UpdateUserAdditionalLanguageUseCase {

    /**
     * (유학생) 언어 - ETC 수정하기 유스케이스
     */
    void execute(UpdateAdditionalLanguageCommand command);
}
