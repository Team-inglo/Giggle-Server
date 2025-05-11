package com.inglo.giggle.security.temporarytoken.application.port.in.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.temporarytoken.application.port.in.command.CreateTemporaryTokenCommand;

@UseCase
public interface CreateTemporaryTokenUseCase {
    /**
     * 임시 토큰 생성 UseCase
     */
     void execute(CreateTemporaryTokenCommand command);
}
