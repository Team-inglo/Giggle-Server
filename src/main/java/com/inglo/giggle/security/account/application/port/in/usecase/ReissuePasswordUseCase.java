package com.inglo.giggle.security.account.application.port.in.usecase;

import com.inglo.giggle.security.account.application.port.in.command.ReissuePasswordCommand;

public interface ReissuePasswordUseCase {

    /**
     * temporaryToken을 이용하여 임시 비밀번호를 발급하는 유스케이스
     */
    void execute(ReissuePasswordCommand command);
}
