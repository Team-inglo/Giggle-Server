package com.inglo.giggle.owner.application.port.in.usecase;

import com.inglo.giggle.owner.application.port.in.command.UpdateOwnerCommand;

public interface UpdateOwnerUseCase {

    /**
     * 고용주 정보 수정하기 UseCase
     */
    void execute(UpdateOwnerCommand command);
}
