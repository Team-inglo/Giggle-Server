package com.inglo.giggle.security.account.application.port.in.usecase;

import com.inglo.giggle.security.account.application.port.in.command.ChangePasswordCommand;

public interface ChangePasswordUseCase {

        /**
        * 비밀번호 변경 유스케이스
        */
        void execute(ChangePasswordCommand command);
}
