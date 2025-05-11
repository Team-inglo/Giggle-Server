package com.inglo.giggle.security.authenticationcode.application.port.in.usecase;

import com.inglo.giggle.security.authenticationcode.application.port.in.command.ValidateAuthenticationCodeCommand;
import com.inglo.giggle.security.authenticationcode.application.port.in.result.ValidateAuthenticationCodeResult;

public interface ValidateAuthenticationCodeUseCase {

    /**
     * 이메일 인증 코드 발급
     */
    ValidateAuthenticationCodeResult execute(ValidateAuthenticationCodeCommand command);
}
