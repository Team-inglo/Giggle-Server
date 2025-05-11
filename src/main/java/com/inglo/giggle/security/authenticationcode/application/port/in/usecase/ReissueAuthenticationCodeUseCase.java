package com.inglo.giggle.security.authenticationcode.application.port.in.usecase;

import com.inglo.giggle.security.authenticationcode.application.port.in.command.ReissueAuthenticationCodeCommand;
import com.inglo.giggle.security.authenticationcode.application.port.in.result.ReissueAuthenticationCodeResult;

public interface ReissueAuthenticationCodeUseCase {
    /**
     * 이메일 인증 코드 발급 UseCase
     */
    ReissueAuthenticationCodeResult execute(ReissueAuthenticationCodeCommand command);
}
