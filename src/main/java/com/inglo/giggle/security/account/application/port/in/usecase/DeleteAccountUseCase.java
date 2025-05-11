package com.inglo.giggle.security.account.application.port.in.usecase;

import com.inglo.giggle.security.account.application.port.in.command.DeleteAccountCommand;

public interface DeleteAccountUseCase {

    /**
     * 계정 탈퇴
     */
    void execute(DeleteAccountCommand command);
}
