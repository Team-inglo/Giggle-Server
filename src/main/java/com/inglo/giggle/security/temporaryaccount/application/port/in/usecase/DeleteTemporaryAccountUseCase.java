package com.inglo.giggle.security.temporaryaccount.application.port.in.usecase;

import com.inglo.giggle.security.temporaryaccount.application.port.in.command.DeleteTemporaryAccountCommand;

public interface DeleteTemporaryAccountUseCase {

    /**
     * 임시 계정 삭제
     *
     */
    void execute(DeleteTemporaryAccountCommand command);
}
