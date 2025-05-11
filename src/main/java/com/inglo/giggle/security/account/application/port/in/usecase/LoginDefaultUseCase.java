package com.inglo.giggle.security.account.application.port.in.usecase;

import com.inglo.giggle.security.account.application.port.in.command.LoginDefaultCommand;

public interface LoginDefaultUseCase {

    /**
     * Security에서 사용되는 Login 유스케이스
     */
    void execute(LoginDefaultCommand command);
}
