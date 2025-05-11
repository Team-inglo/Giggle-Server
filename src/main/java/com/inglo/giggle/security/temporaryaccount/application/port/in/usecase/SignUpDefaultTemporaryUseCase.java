package com.inglo.giggle.security.temporaryaccount.application.port.in.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.temporaryaccount.application.port.in.command.SignUpDefaultTemporaryCommand;
import com.inglo.giggle.security.temporaryaccount.application.port.in.result.SignUpDefaultTemporaryResult;

@UseCase
public interface SignUpDefaultTemporaryUseCase {
    /**
     * 임시 회원가입 유스케이스
     */
     SignUpDefaultTemporaryResult execute(SignUpDefaultTemporaryCommand command);
}
