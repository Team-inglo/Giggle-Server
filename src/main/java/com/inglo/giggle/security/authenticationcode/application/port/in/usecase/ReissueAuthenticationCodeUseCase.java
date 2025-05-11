package com.inglo.giggle.security.authenticationcode.application.port.in.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.account.adapter.in.web.dto.IssueAuthenticationCodeRequestDto;
import com.inglo.giggle.security.authenticationcode.application.port.in.command.ReissueAuthenticationCodeCommand;
import com.inglo.giggle.security.authenticationcode.application.port.in.result.ReissueAuthenticationCodeResult;

@UseCase
public interface ReissueAuthenticationCodeUseCase {
    /**
     * 이메일 인증 코드 발급 UseCase
     */
    ReissueAuthenticationCodeResult execute(ReissueAuthenticationCodeCommand command);
}
