package com.inglo.giggle.resume.application.port.in.usecase;

import com.inglo.giggle.resume.application.port.in.command.DeleteAdditionalLanguageCommand;

public interface DeleteUserAdditionalLanguageUseCase {

    /**
     * (유학생) 언어 - ETC 삭제하기 유스케이스
     */
    void execute(DeleteAdditionalLanguageCommand command);
}
