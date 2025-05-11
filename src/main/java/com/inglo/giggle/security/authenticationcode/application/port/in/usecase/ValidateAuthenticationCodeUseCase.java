package com.inglo.giggle.security.authenticationcode.application.port.in.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.authenticationcode.application.port.in.command.ValidateAuthenticationCodeCommand;
import com.inglo.giggle.security.authenticationcode.application.port.in.result.ValidateAuthenticationCodeResult;

@UseCase
public interface ValidateAuthenticationCodeUseCase {

    /**
     * 이메일 인증 코드 발급
     */
    ValidateAuthenticationCodeResult execute(ValidateAuthenticationCodeCommand command);
}
