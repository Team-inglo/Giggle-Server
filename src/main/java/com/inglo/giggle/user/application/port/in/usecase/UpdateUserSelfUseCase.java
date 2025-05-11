package com.inglo.giggle.user.application.port.in.usecase;

import com.inglo.giggle.user.application.port.in.command.UpdateUserSelfCommand;

public interface UpdateUserSelfUseCase {

    /**
     * 유저 정보 수정하기 UseCase
     */
    void execute(UpdateUserSelfCommand command);
}
