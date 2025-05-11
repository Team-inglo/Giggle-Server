package com.inglo.giggle.termaccount.application.port.in.usecase;

import com.inglo.giggle.termaccount.application.port.in.command.CreateTermAccountCommand;

public interface CreateTermAccountUseCase {
    /**
     * 사용자 약관 동의하기 유스케이스
     */
    void execute(CreateTermAccountCommand command);
}
