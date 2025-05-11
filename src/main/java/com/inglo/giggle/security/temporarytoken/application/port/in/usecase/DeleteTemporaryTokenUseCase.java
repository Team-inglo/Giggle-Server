package com.inglo.giggle.security.temporarytoken.application.port.in.usecase;

import com.inglo.giggle.security.temporarytoken.application.port.in.command.DeleteTemporaryTokenCommand;

public interface DeleteTemporaryTokenUseCase {
    /**
     * 임시 토큰 삭제
     *
     */
    void execute(DeleteTemporaryTokenCommand command);
}
