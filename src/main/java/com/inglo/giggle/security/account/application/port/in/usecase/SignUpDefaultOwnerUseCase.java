package com.inglo.giggle.security.account.application.port.in.usecase;

import com.inglo.giggle.security.account.application.port.in.command.SignUpDefaultOwnerCommand;
import com.inglo.giggle.security.account.application.port.in.result.SignUpDefaultOwnerResult;

public interface SignUpDefaultOwnerUseCase {

    /**
     * 점주 로그인 전용 회원가입 유스케이스
     */
    SignUpDefaultOwnerResult execute(SignUpDefaultOwnerCommand command);
}
