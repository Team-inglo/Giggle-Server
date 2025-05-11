package com.inglo.giggle.security.authenticationcodehistory.application.port.in.usecase;

import com.inglo.giggle.security.authenticationcodehistory.application.port.in.command.DeleteAuthenticationCodeHistoryCommand;

public interface DeleteAuthenticationCodeHistoryUseCase {

    /**
     * 인증 코드 발급 이력 삭제
     */
    void execute(DeleteAuthenticationCodeHistoryCommand command);
}
