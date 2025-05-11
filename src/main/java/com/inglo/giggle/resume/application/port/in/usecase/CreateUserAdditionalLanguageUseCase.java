package com.inglo.giggle.resume.application.port.in.usecase;

import com.inglo.giggle.resume.application.port.in.command.CreateAdditionalLanguageCommand;

public interface CreateUserAdditionalLanguageUseCase {

    /**
     * (유학생) 언어 - ETC 생성하기 유스케이스
     */
    void execute(CreateAdditionalLanguageCommand command);
}
