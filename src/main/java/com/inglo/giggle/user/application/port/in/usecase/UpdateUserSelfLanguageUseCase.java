package com.inglo.giggle.user.application.port.in.usecase;

import com.inglo.giggle.user.application.port.in.command.UpdateUserSelfLanguageCommand;

public interface UpdateUserSelfLanguageUseCase {

    /**
     * 유저 언어 변경하기 UseCase
     */
    void execute(UpdateUserSelfLanguageCommand command);
}
