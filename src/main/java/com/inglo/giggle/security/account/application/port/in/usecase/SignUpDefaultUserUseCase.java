package com.inglo.giggle.security.account.application.port.in.usecase;

import com.inglo.giggle.security.account.application.port.in.command.SignUpDefaultUserCommand;
import com.inglo.giggle.security.account.application.port.in.result.SignUpDefaultUserResult;

public interface SignUpDefaultUserUseCase {

    /**
     * 유학생 로그인 전용 회원가입 유스케이스
     */
    SignUpDefaultUserResult execute(SignUpDefaultUserCommand command);
}
